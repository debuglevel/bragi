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

    /**
     * Resizes an Image. Does not maintain aspect ratio.
     */
    fun resizeImage(
        image: BufferedImage,
        targetDimension: Dimension
    ): BufferedImage {
        logger.trace { "Resizing image width=${image.width} height=${image.height} to width=${targetDimension.width} height=${targetDimension.height}..." }

        if (targetDimension.height <= 0 || targetDimension.width <= 0) {
            throw IllegalArgumentException("width and height must be greater than 0")
        }

        val resizedImage = BufferedImage(targetDimension.width, targetDimension.height, BufferedImage.TYPE_INT_RGB)
        val graphics2D = resizedImage.createGraphics()
        graphics2D.drawImage(image, 0, 0, targetDimension.width, targetDimension.height, null)
        graphics2D.dispose()

        logger.trace { "Resized image width=${image.width} height=${image.height} to width=${targetDimension.width} height=${targetDimension.height}" }
        return resizedImage
    }

    /**
     * Gets an Image from a ByteArray.
     * See: https://stackoverflow.com/a/13465476/4764279
     */
    fun buildImageFromBytes(imageBytes: ByteArray): BufferedImage {
        logger.trace { "Creating Image from bytes (size=${imageBytes.size})..." }

        val byteArrayInputStream = ByteArrayInputStream(imageBytes)
        val bufferedImage = ImageIO.read(byteArrayInputStream)

        logger.trace { "Created Image from bytes (size=${imageBytes.size})" }
        return bufferedImage
    }

    /**
     * Gets a ByteArray from an Image.
     * @implNote Current implementation always returns a JPG.
     * See: https://stackoverflow.com/a/15414490/4764279
     */
    fun buildBytesFromImage(image: BufferedImage): ByteArray {
        logger.trace { "Creating bytes from Image..." }

        val byteArrayOutputStream = ByteArrayOutputStream()
        ImageIO.write(image, "jpg", byteArrayOutputStream)
        val bytes = byteArrayOutputStream.toByteArray()

        logger.trace { "Created bytes (size=${bytes.size}) from Image" }
        return bytes
    }

    /**
     * Calculates the image dimensions when scaled down to a boundary while maintaining the aspect ratio.
     * Does not scale up image dimensions.
     * See: https://stackoverflow.com/a/10245583/4764279
     */
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

    /**
     * Gets the image format of an image. JREs may differ in their image type support.
     * See: https://stackoverflow.com/a/26122845/4764279
     */
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