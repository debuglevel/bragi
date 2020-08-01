package de.debuglevel.bragi.character

import io.micronaut.test.annotation.MicronautTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import javax.inject.Inject

@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CharacterClientTests {

    @Inject
    lateinit var characterClient: CharacterClient

    @ParameterizedTest
    @MethodSource("addItemRequestProvider")
    fun `add character`(addCharacterRequest: AddCharacterRequest) {
        // Arrange

        // Act
        val addedItem = characterClient.postOne(addCharacterRequest).blockingGet()

        // Assert
        Assertions.assertThat(addedItem.name).isEqualTo(addCharacterRequest.name)
    }

    @ParameterizedTest
    @MethodSource("addItemRequestProvider")
    fun `retrieve character`(addCharacterRequest: AddCharacterRequest) {
        // Arrange
        val addedItem = characterClient.postOne(addCharacterRequest).blockingGet()

        // Act
        val retrievedItem = characterClient.getOne(addedItem.id!!).blockingGet()

        // Assert
        Assertions.assertThat(retrievedItem.id).isEqualTo(addedItem.id)
        Assertions.assertThat(retrievedItem.name).isEqualTo(addedItem.name)
    }

    fun addItemRequestProvider() = CharacterTestDataProvider.itemProvider()
        .map {
            AddCharacterRequest(it.name)
        }
}