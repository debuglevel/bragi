package de.debuglevel.bragi.character

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CharacterUtilsTests {
    @ParameterizedTest
    @MethodSource("firstnameItemProvider")
    fun `get first name from name`(firstnameTestData: CharacterTestDataProvider.FirstnameTestData) {
        // Arrange

        // Act
        val firstName = CharacterUtils.extractFirstName(firstnameTestData.name)

        // Assert
        Assertions.assertThat(firstName).isEqualTo(firstnameTestData.firstName)
    }

    @Test
    fun `get first name from empty string`() {
        // Arrange

        // Act & Assert
        org.junit.jupiter.api.assertThrows<IllegalArgumentException> { CharacterUtils.extractFirstName("") }
        org.junit.jupiter.api.assertThrows<IllegalArgumentException> { CharacterUtils.extractFirstName(" ") }
    }

    fun firstnameItemProvider() = CharacterTestDataProvider.firstnameItemProvider()
}