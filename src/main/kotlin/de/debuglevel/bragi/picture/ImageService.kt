package de.debuglevel.bragi.picture

import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO
import javax.inject.Singleton


@Singleton
class ImageService {
    fun resizeImage(
        originalImage: BufferedImage,
        targetWidth: Int,
        targetHeight: Int
    ): BufferedImage {
        val resizedImage = BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB)
        val graphics2D = resizedImage.createGraphics()
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null)
        graphics2D.dispose()
        return resizedImage
    }

    // https://stackoverflow.com/a/13465476/4764279
    fun createImageFromBytes(imageData: ByteArray): BufferedImage {
        val byteArrayInputStream = ByteArrayInputStream(imageData)
        val bufferedImage = ImageIO.read(byteArrayInputStream)
        return bufferedImage
    }

    // https://stackoverflow.com/a/15414490/4764279
    fun createBytesFromImage(image: BufferedImage): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        ImageIO.write(image, "jpg", byteArrayOutputStream)
        val bytes = byteArrayOutputStream.toByteArray()
        return bytes
    }
}