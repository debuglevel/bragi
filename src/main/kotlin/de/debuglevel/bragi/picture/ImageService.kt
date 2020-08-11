package de.debuglevel.bragi.picture

import mu.KotlinLogging
import java.awt.Dimension
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO
import javax.inject.Singleton


@Singleton
class ImageService {
    private val logger = KotlinLogging.logger {}

    fun resizeImage(
        image: BufferedImage,
        targetDimension: Dimension
    ): BufferedImage {
        logger.trace { "Resizing image width=${image.width} height=${image.height} to width=${targetDimension.width} height=${targetDimension.height}..." }

        val resizedImage = BufferedImage(targetDimension.width, targetDimension.height, BufferedImage.TYPE_INT_RGB)
        val graphics2D = resizedImage.createGraphics()
        graphics2D.drawImage(image, 0, 0, targetDimension.width, targetDimension.height, null)
        graphics2D.dispose()

        logger.trace { "Resized image width=${image.width} height=${image.height} to width=${targetDimension.width} height=${targetDimension.height}" }
        return resizedImage
    }

    // https://stackoverflow.com/a/13465476/4764279
    fun createImageFromBytes(imageBytes: ByteArray): BufferedImage {
        logger.trace { "Creating Image from bytes..." }

        val byteArrayInputStream = ByteArrayInputStream(imageBytes)
        val bufferedImage = ImageIO.read(byteArrayInputStream)

        logger.trace { "Created Image from bytes" }
        return bufferedImage
    }

    // https://stackoverflow.com/a/15414490/4764279
    fun createBytesFromImage(image: BufferedImage): ByteArray {
        logger.trace { "Creating bytes from Image..." }

        val byteArrayOutputStream = ByteArrayOutputStream()
        ImageIO.write(image, "jpg", byteArrayOutputStream)
        val bytes = byteArrayOutputStream.toByteArray()

        logger.trace { "Created bytes from Image" }
        return bytes
    }

    // https://stackoverflow.com/a/10245583/4764279
    fun getScaledDimension(imageSize: Dimension, boundary: Dimension): Dimension {
        logger.trace { "Getting scaled dimension for $imageSize inside boundary $boundary..." }

        if (imageSize.height <= 0 || imageSize.width <= 0 || boundary.height <= 0 || boundary.width <= 0) {
            throw IllegalArgumentException("widths and heights must be greater than 0")
        }

        var newWidth = imageSize.width
        var newHeight = imageSize.height

        // first check if we need to scale width
        if (imageSize.width > boundary.width) { // scale width to fit
            newWidth = boundary.width
            // scale height to maintain aspect ratio
            newHeight = newWidth * imageSize.height / imageSize.width
        }

        // then check if we need to scale even with the new height
        if (newHeight > boundary.height) { // scale height to fit instead
            newHeight = boundary.height
            // scale width to maintain aspect ratio
            newWidth = newHeight * imageSize.width / imageSize.height
        }

        val scaledSize = Dimension(newWidth, newHeight)
        logger.trace { "Got scaled dimension for $imageSize inside boundary $boundary: $scaledSize" }
        return scaledSize
    }

    // https://stackoverflow.com/a/26122845/4764279
    fun getImageFormat(imageBytes: ByteArray): ImageFormat {
        logger.trace { "Getting format from bytes..." }
        val imageInputStream = ImageIO.createImageInputStream(ByteArrayInputStream(imageBytes))

        val formatName = ImageIO.getImageReaders(imageInputStream)
            .asSequence()
            .map { it.formatName }
            .firstOrNull()
        logger.trace { "Got format name from bytes: $formatName" }

        val format = when (formatName) {
            "png" -> ImageFormat.PNG
            "bmp" -> ImageFormat.BMP
            "gif" -> ImageFormat.GIF
            "JPEG" -> ImageFormat.JPEG
            else -> throw UnknownImageFormat(formatName)
        }

        logger.trace { "Got format from bytes: $format" }
        return format
    }

    class UnknownImageFormat(format: String?) : Exception("Detected image format '$format' is unknown.")
}