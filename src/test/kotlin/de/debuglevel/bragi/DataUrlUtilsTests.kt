package de.debuglevel.bragi

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DataUrlUtilsTests {
    @ParameterizedTest
    @MethodSource("base64DataUrlProvider")
    fun `get base64 part from base64 data URL`(dataUrlTestData: DataUrlTestData) {
        // Arrange

        // Act
        val base64 = DataUrlUtils.getBase64Part(dataUrlTestData.dataUrl)

        // Assert
        Assertions.assertThat(base64).isEqualTo(dataUrlTestData.base64)
    }

    @ParameterizedTest
    @MethodSource("invalidBase64DataUrlProvider")
    fun `get base64 part from non-base64 data URL`(dataUrlTestData: DataUrlTestData) {
        // Arrange

        // Act & Assert
        org.junit.jupiter.api.assertThrows<IllegalArgumentException> { DataUrlUtils.getBase64Part(dataUrlTestData.dataUrl) }
    }

    data class DataUrlTestData(val dataUrl: String?, val base64: String? = null)

    fun base64DataUrlProvider(): List<DataUrlTestData> {
        return listOf(
            DataUrlTestData("data:text/vnd-example+xyz;foo=bar;base64,R0lGODdh", "R0lGODdh"),
            DataUrlTestData("data:image/*;base64,R0lGODdh", "R0lGODdh"),
            DataUrlTestData("data:base64,R0lGODdh", "R0lGODdh"),
            DataUrlTestData("data:base64,", "")
        )
    }

    fun invalidBase64DataUrlProvider(): List<DataUrlTestData> {
        return listOf(
            DataUrlTestData("data:text/plain;charset=UTF-8;page=21,the%20data:1234,5678"),
            DataUrlTestData("data:,"),
            DataUrlTestData("data:"),
            DataUrlTestData("data:base64"),
            DataUrlTestData("base64,"),
            DataUrlTestData("base64"),
            DataUrlTestData("whatever"),
            DataUrlTestData(""),
            DataUrlTestData(null)
        )
    }
}