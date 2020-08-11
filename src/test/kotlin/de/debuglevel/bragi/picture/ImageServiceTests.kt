package de.debuglevel.bragi.picture

import io.micronaut.test.annotation.MicronautTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.awt.Dimension
import java.util.*
import javax.inject.Inject

@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ImageServiceTests {
    @Inject
    lateinit var imageService: ImageService

    @Test
    fun resizeImage() {
    }

    @Test
    fun createImageFromBytes() {
    }

    @Test
    fun createBytesFromImage() {
    }

    @ParameterizedTest
    @MethodSource("scaleImageProvider")
    fun `get scaled image dimensions`(scaleImageTestData: ScaleImageTestData) {
        // Arrange

        // Act
        val result = imageService.getScaledDimension(scaleImageTestData.original, scaleImageTestData.boundary)

        // Assert
        Assertions.assertThat(result).isEqualTo(scaleImageTestData.result)
    }

    @Test
    fun `get scaled image dimensions for invalid values`() {
        // Arrange

        // Act & Assert
        assertThrows<IllegalArgumentException> {
            imageService.getScaledDimension(
                Dimension(-10, 10),
                Dimension(10, 10)
            )
        }
        assertThrows<IllegalArgumentException> {
            imageService.getScaledDimension(
                Dimension(10, -10),
                Dimension(10, 10)
            )
        }
        assertThrows<IllegalArgumentException> {
            imageService.getScaledDimension(
                Dimension(10, 10),
                Dimension(-10, 10)
            )
        }
        assertThrows<IllegalArgumentException> {
            imageService.getScaledDimension(
                Dimension(10, 10),
                Dimension(10, -10)
            )
        }
        assertThrows<IllegalArgumentException> { imageService.getScaledDimension(Dimension(0, 10), Dimension(10, 10)) }
        assertThrows<IllegalArgumentException> { imageService.getScaledDimension(Dimension(10, 0), Dimension(10, 10)) }
        assertThrows<IllegalArgumentException> { imageService.getScaledDimension(Dimension(10, 10), Dimension(0, 10)) }
        assertThrows<IllegalArgumentException> { imageService.getScaledDimension(Dimension(10, 10), Dimension(10, 0)) }
    }

    data class ScaleImageTestData(val original: Dimension, val boundary: Dimension, val result: Dimension)

    fun scaleImageProvider(): List<ScaleImageTestData> {
        return listOf(
            // scale to the same boundary
            ScaleImageTestData(
                original = Dimension(100, 100),
                boundary = Dimension(100, 100),
                result = Dimension(100, 100)
            ),
            // do not scale up
            ScaleImageTestData(
                original = Dimension(100, 100),
                boundary = Dimension(200, 200),
                result = Dimension(100, 100)
            ),
            // scale down a square in a square boundary
            ScaleImageTestData(
                original = Dimension(100, 100),
                boundary = Dimension(50, 50),
                result = Dimension(50, 50)
            ),
            // scale down a square in a rectangle boundary
            ScaleImageTestData(
                original = Dimension(100, 100),
                boundary = Dimension(25, 50),
                result = Dimension(25, 25)
            ),
            ScaleImageTestData(
                original = Dimension(100, 100),
                boundary = Dimension(50, 25),
                result = Dimension(25, 25)
            ),
            // scale down a rectangle to a square boundary
            ScaleImageTestData(
                original = Dimension(200, 100),
                boundary = Dimension(100, 100),
                result = Dimension(100, 50)
            ),
            ScaleImageTestData(
                original = Dimension(100, 200),
                boundary = Dimension(100, 100),
                result = Dimension(50, 100)
            ),
            ScaleImageTestData(
                original = Dimension(200, 100),
                boundary = Dimension(50, 50),
                result = Dimension(50, 25)
            ),
            ScaleImageTestData(
                original = Dimension(100, 200),
                boundary = Dimension(50, 50),
                result = Dimension(25, 50)
            )
        )
    }

    @ParameterizedTest
    @MethodSource("imageFormatProvider")
    fun `get image format`(imageFormatTestData: ImageFormatTestData) {
        // Arrange

        // Act
        val imageFormat = imageService.getImageFormat(imageFormatTestData.bytes)

        // Assert
        Assertions.assertThat(imageFormat).isEqualTo(imageFormatTestData.format)
    }

    @ParameterizedTest
    @MethodSource("invalidImageFormatProvider")
    fun `get image format for invalid image`(imageFormatTestData: ImageFormatTestData) {
        // Arrange

        // Act & Assert
        org.junit.jupiter.api.assertThrows<ImageService.UnknownImageFormat> {
            imageService.getImageFormat(
                imageFormatTestData.bytes
            )
        }
    }

    data class ImageFormatTestData(val bytes: ByteArray, val format: ImageFormat? = null)

    fun imageFormatProvider(): List<ImageFormatTestData> {
        return listOf(
            ImageFormatTestData(
                Base64.getDecoder().decode("iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAIAAACQd1PeAAAACXBIWXMAAC4jAAAuIwF4pT92AAAAB3RJTUUH5AgKFgwt7w2UKgAAABl0RVh0Q29tbWVudABDcmVhdGVkIHdpdGggR0lNUFeBDhcAAAAMSURBVAjXY/j38wEABdAC2BYnoOwAAAAASUVORK5CYII="),
                ImageFormat.PNG
            ),
            ImageFormatTestData(
                Base64.getDecoder().decode("/9j/4AAQSkZJRgABAQEBLAEsAAD//gATQ3JlYXRlZCB3aXRoIEdJTVD/2wBDAAMCAgMCAgMDAwMEAwMEBQgFBQQEBQoHBwYIDAoMDAsKCwsNDhIQDQ4RDgsLEBYQERMUFRUVDA8XGBYUGBIUFRT/2wBDAQMEBAUEBQkFBQkUDQsNFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBT/wgARCAABAAEDAREAAhEBAxEB/8QAFAABAAAAAAAAAAAAAAAAAAAACP/EABUBAQEAAAAAAAAAAAAAAAAAAAMF/9oADAMBAAIQAxAAAAFQTW//xAAUEAEAAAAAAAAAAAAAAAAAAAAA/9oACAEBAAEFAn//xAAUEQEAAAAAAAAAAAAAAAAAAAAA/9oACAEDAQE/AX//xAAUEQEAAAAAAAAAAAAAAAAAAAAA/9oACAECAQE/AX//xAAUEAEAAAAAAAAAAAAAAAAAAAAA/9oACAEBAAY/An//xAAUEAEAAAAAAAAAAAAAAAAAAAAA/9oACAEBAAE/IX//2gAMAwEAAgADAAAAEH//xAAUEQEAAAAAAAAAAAAAAAAAAAAA/9oACAEDAQE/EH//xAAUEQEAAAAAAAAAAAAAAAAAAAAA/9oACAECAQE/EH//xAAUEAEAAAAAAAAAAAAAAAAAAAAA/9oACAEBAAE/EH//2Q=="),
                ImageFormat.JPEG
            ),
            ImageFormatTestData(
                Base64.getDecoder().decode("R0lGODdhAQABAIAAAP754P754CwAAAAAAQABAAACAkQBADs="),
                ImageFormat.GIF
            ),
            ImageFormatTestData(
                Base64.getDecoder().decode("Qk1+AAAAAAAAAHoAAABsAAAAAQAAAAEAAAABABgAAAAAAAQAAAAjLgAAIy4AAAAAAAAAAAAAQkdScwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAIAAAAAAAAAAAAAAAAAAADg+f4A"),
                ImageFormat.BMP
            )
            //ImageFormatTestData(Base64.getDecoder().decode("UklGRjIAAABXRUJQVlA4ICYAAABQAQCdASoBAAEAAMASJQBOgCgAAP7+wfP/wH4g/HOLZV6vK4AAAA=="), "webp"),
            //ImageFormatTestData(Base64.getDecoder().decode("JVBERi0xLjUKJbXtrvsKNCAwIG9iago8PCAvTGVuZ3RoIDUgMCBSCiAgIC9GaWx0ZXIgL0ZsYXRlRGVjb2RlCj4+CnN0cmVhbQp4nDNUMABCXUMQpWdkopCcy1XIFcgFADCwBFQKZW5kc3RyZWFtCmVuZG9iago1IDAgb2JqCiAgIDI3CmVuZG9iagozIDAgb2JqCjw8Cj4+CmVuZG9iagoyIDAgb2JqCjw8IC9UeXBlIC9QYWdlICUgMQogICAvUGFyZW50IDEgMCBSCiAgIC9NZWRpYUJveCBbIDAgMCAwLjI0IDAuMjQgXQogICAvQ29udGVudHMgNCAwIFIKICAgL0dyb3VwIDw8CiAgICAgIC9UeXBlIC9Hcm91cAogICAgICAvUyAvVHJhbnNwYXJlbmN5CiAgICAgIC9JIHRydWUKICAgICAgL0NTIC9EZXZpY2VSR0IKICAgPj4KICAgL1Jlc291cmNlcyAzIDAgUgo+PgplbmRvYmoKMSAwIG9iago8PCAvVHlwZSAvUGFnZXMKICAgL0tpZHMgWyAyIDAgUiBdCiAgIC9Db3VudCAxCj4+CmVuZG9iago2IDAgb2JqCjw8IC9Qcm9kdWNlciAoY2Fpcm8gMS4xNS4xMiAoaHR0cDovL2NhaXJvZ3JhcGhpY3Mub3JnKSkKICAgL0NyZWF0aW9uRGF0ZSAoRDoyMDIwMDgxMTAwMTM1NSswMicwMCkKPj4KZW5kb2JqCjcgMCBvYmoKPDwgL1R5cGUgL0NhdGFsb2cKICAgL1BhZ2VzIDEgMCBSCj4+CmVuZG9iagp4cmVmCjAgOAowMDAwMDAwMDAwIDY1NTM1IGYgCjAwMDAwMDAzODEgMDAwMDAgbiAKMDAwMDAwMDE2MSAwMDAwMCBuIAowMDAwMDAwMTQwIDAwMDAwIG4gCjAwMDAwMDAwMTUgMDAwMDAgbiAKMDAwMDAwMDExOSAwMDAwMCBuIAowMDAwMDAwNDQ2IDAwMDAwIG4gCjAwMDAwMDA1NjIgMDAwMDAgbiAKdHJhaWxlcgo8PCAvU2l6ZSA4CiAgIC9Sb290IDcgMCBSCiAgIC9JbmZvIDYgMCBSCj4+CnN0YXJ0eHJlZgo2MTQKJSVFT0YK"), "pdf")
        )
    }

    fun invalidImageFormatProvider(): List<ImageFormatTestData> {
        return listOf(
            ImageFormatTestData(Base64.getDecoder().decode("")),
            ImageFormatTestData(Base64.getDecoder().decode("dGVzdAo=")),
            ImageFormatTestData(Base64.getDecoder().decode("UklGRjIAAABXRUJQVlA4ICYAAABQAQCdASoBAAEAAMASJQBOgCgAAP7+wfP/wH4g/HOLZV6vK4AAAA==")), // WEBP
            ImageFormatTestData(
                Base64.getDecoder().decode(
                    "JVBERi0xLjUKJbXtrvsKNCAwIG9iago8PCAvTGVuZ3RoIDUgMCBSCiAgIC9GaWx0ZXIgL0ZsYXRlRGVjb2RlCj4+CnN0cmVhbQp4nDNUMABCXUMQpWdkopCcy1XIFcgFADCwBFQKZW5kc3RyZWFtCmVuZG9iago1IDAgb2JqCiAgIDI3CmVuZG9iagozIDAgb2JqCjw8Cj4+CmVuZG9iagoyIDAgb2JqCjw8IC9UeXBlIC9QYWdlICUgMQogICAvUGFyZW50IDEgMCBSCiAgIC9NZWRpYUJveCBbIDAgMCAwLjI0IDAuMjQgXQogICAvQ29udGVudHMgNCAwIFIKICAgL0dyb3VwIDw8CiAgICAgIC9UeXBlIC9Hcm91cAogICAgICAvUyAvVHJhbnNwYXJlbmN5CiAgICAgIC9JIHRydWUKICAgICAgL0NTIC9EZXZpY2VSR0IKICAgPj4KICAgL1Jlc291cmNlcyAzIDAgUgo+PgplbmRvYmoKMSAwIG9iago8PCAvVHlwZSAvUGFnZXMKICAgL0tpZHMgWyAyIDAgUiBdCiAgIC9Db3VudCAxCj4+CmVuZG9iago2IDAgb2JqCjw8IC9Qcm9kdWNlciAoY2Fpcm8gMS4xNS4xMiAoaHR0cDovL2NhaXJvZ3JhcGhpY3Mub3JnKSkKICAgL0NyZWF0aW9uRGF0ZSAoRDoyMDIwMDgxMTAwMTM1NSswMicwMCkKPj4KZW5kb2JqCjcgMCBvYmoKPDwgL1R5cGUgL0NhdGFsb2cKICAgL1BhZ2VzIDEgMCBSCj4+CmVuZG9iagp4cmVmCjAgOAowMDAwMDAwMDAwIDY1NTM1IGYgCjAwMDAwMDAzODEgMDAwMDAgbiAKMDAwMDAwMDE2MSAwMDAwMCBuIAowMDAwMDAwMTQwIDAwMDAwIG4gCjAwMDAwMDAwMTUgMDAwMDAgbiAKMDAwMDAwMDExOSAwMDAwMCBuIAowMDAwMDAwNDQ2IDAwMDAwIG4gCjAwMDAwMDA1NjIgMDAwMDAgbiAKdHJhaWxlcgo8PCAvU2l6ZSA4CiAgIC9Sb290IDcgMCBSCiAgIC9JbmZvIDYgMCBSCj4+CnN0YXJ0eHJlZgo2MTQKJSVFT0YK"
                )
            ) // PDF
        )
    }
}