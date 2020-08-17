package de.debuglevel.bragi.picture

import mu.KotlinLogging
import java.awt.Dimension
import java.io.FileNotFoundException
import java.net.URL
import java.util.*
import javax.inject.Singleton

@Singleton
class PictureService(
    private val imageService: ImageService
) {
    private val logger = KotlinLogging.logger {}

    fun getPicture(pictureItem: PictureEntity): ByteArray {
        logger.debug { "Getting picture for $pictureItem..." }

        val picture = pictureItem.picture
        val byteArrayPicture = when {
            picture.isNullOrBlank() -> throw PictureProvider.PictureNotFoundException(pictureItem)
            isUrl(picture) -> {
                // download picture from a URL. This is rather a development shortcut than a real feature (so that the git repository does not got blown up by full size images).
                try {
                    logger.debug { "Downloading picture from URL '$picture'..." }
                    URL(picture).readBytes()
                } catch (e: FileNotFoundException) {
                    throw PictureProvider.PictureNotFoundException(pictureItem)
                }
            }
            else -> Base64.getDecoder().decode(picture)!! // assume Base64 if not empty and not an URL
        }

        logger.debug { "Got picture for $pictureItem" }
        return byteArrayPicture
    }

    /**
     * Checks naively if this string is an URL to some other web server.
     */
    private fun isUrl(url: String): Boolean {
        return url.startsWith("http://") || url.startsWith("https://")
    }

    fun getResizedPicture(pictureItem: PictureEntity, maxWidth: Int?, maxHeight: Int?): ByteArray {
        logger.debug { "Getting resized picture for $pictureItem with maxWidth=$maxWidth, maxHeight=$maxHeight ..." }
        if (maxWidth == null && maxHeight == null) {
            throw IllegalArgumentException("One of maxWidth or maxHeight must not be null")
        }

        val bytes = this.getPicture(pictureItem)

        val image = imageService.buildImageFromBytes(bytes)
        val scaledDimension = imageService.getScaledDimension(
            imageSize = Dimension(image.width, image.height),
            boundary = Dimension(maxWidth ?: Int.MAX_VALUE, maxHeight ?: Int.MAX_VALUE)
        )
        val resizedImage = imageService.resizeImage(image, scaledDimension)
        val resizedBytes = imageService.buildBytesFromImage(resizedImage)

        logger.debug { "Got resized picture for $pictureItem" }
        return resizedBytes
    }
}