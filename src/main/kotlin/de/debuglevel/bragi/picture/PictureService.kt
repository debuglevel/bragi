package de.debuglevel.bragi.picture

import mu.KotlinLogging
import java.awt.Dimension
import java.util.*
import javax.inject.Singleton

@Singleton
class PictureService(
    private val imageService: ImageService
) {
    private val logger = KotlinLogging.logger {}

    fun getPicture(pictureItem: PictureEntity): ByteArray {
        logger.debug { "Getting picture for $pictureItem..." }

        val base64picture = pictureItem.picture
        val byteArrayPicture = when {
            !base64picture.isNullOrBlank() -> Base64.getDecoder().decode(base64picture)!!
            else -> throw PictureProvider.PictureNotFoundException(pictureItem)
        }

        logger.debug { "Got picture for $pictureItem" }
        return byteArrayPicture
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