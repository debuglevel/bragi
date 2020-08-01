package de.debuglevel.bragi.character

import io.micronaut.test.annotation.MicronautTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import javax.inject.Inject

@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CharacterServiceTests {

    @Inject
    lateinit var characterService: CharacterService

    @ParameterizedTest
    @MethodSource("itemProvider")
    fun `add character`(character: Character) {
        // Arrange

        // Act
        val addedItem = characterService.add(character)

        // Assert
        assertThat(addedItem).isEqualTo(character)
    }

    @ParameterizedTest
    @MethodSource("itemProvider")
    fun `retrieve character`(character: Character) {
        // Arrange
        val addedItem = characterService.add(character)

        // Act
        val retrievedItem = characterService.get(addedItem.id!!)

        // Assert
        assertThat(retrievedItem).isEqualTo(addedItem)
    }

    @Test
    fun `update character`() {
        // Arrange
        val item = Character(
            null,
            name = "Original Name",
            notes = "Original Notes",
            aliases = mutableListOf("Original Alias1", "Original Alias2")
        )
        val addedItem = characterService.add(item)

        // Act
        val retrievedItem = characterService.get(addedItem.id!!)
        retrievedItem.apply {
            name = "Updated Name"
            notes = "Updated Notes"
            aliases = mutableListOf("Updated Alias1", "Updated Alias2", "Updated Alias3")
        }
        val updatedItem = characterService.update(retrievedItem.id!!, retrievedItem)

        // Assert
        assertThat(updatedItem.name).isEqualTo("Updated Name")
        assertThat(updatedItem.notes).isEqualTo("Updated Notes")
        assertThat(updatedItem.aliases).containsAll(listOf("Updated Alias1", "Updated Alias2", "Updated Alias3"))
    }

    /**
     * Test updating a copy of the original entity, because this way it's ensured that the service can handle detached entities.
     */
    @Test
    fun `update character detached with copy()`() {
        // Arrange
        val item = Character(
            null,
            name = "Original Title",
            notes = "Original Notes",
            aliases = mutableListOf("Original Alias1", "Original Alias2")
        )
        val savedItem = characterService.add(item)

        // Act
        val retrievedItem = characterService.get(savedItem.id!!)
        val updateItem = retrievedItem.copy(
            name = "Updated Title",
            notes = "Updated Notes",
            aliases = mutableListOf("Updated Alias1", "Updated Alias2", "Updated Alias3")
        )
        val updatedItem = characterService.update(updateItem.id!!, updateItem)

        // Assert
        assertThat(updatedItem.name).isEqualTo("Updated Title")
        assertThat(updatedItem.notes).isEqualTo("Updated Notes")
        assertThat(updatedItem.aliases).containsAll(listOf("Updated Alias1", "Updated Alias2", "Updated Alias3"))
    }

    fun itemProvider() = CharacterTestDataProvider.itemProvider()
}