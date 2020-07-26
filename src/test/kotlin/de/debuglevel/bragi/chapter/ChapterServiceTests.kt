package de.debuglevel.bragi.chapter

import io.micronaut.test.annotation.MicronautTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import javax.inject.Inject

@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ChapterServiceTests {

    @Inject
    lateinit var chapterService: ChapterService

    @ParameterizedTest
    @MethodSource("itemProvider")
    fun `add chapter`(chapter: Chapter) {
        // Arrange

        // Act
        val addedItem = chapterService.add(chapter)

        // Assert
        assertThat(addedItem).isEqualTo(chapter)
    }

    @ParameterizedTest
    @MethodSource("itemProvider")
    fun `retrieve chapter`(chapter: Chapter) {
        // Arrange
        val addedItem = chapterService.add(chapter)

        // Act
        val retrievedItem = chapterService.get(addedItem.id!!)

        // Assert
        assertThat(retrievedItem).isEqualTo(addedItem)
    }

    @Test
    fun `update chapter`() {
        // Arrange
        val item = Chapter(null, "Original Title", content = "Original Content")
        val addedItem = chapterService.add(item)

        // Act
        val retrievedItem = chapterService.get(addedItem.id!!)
        retrievedItem.apply {
            title = "Updated Title"
            content = "Updated Content"
        }
        val updatedItem = chapterService.update(retrievedItem.id!!, retrievedItem)

        // Assert
        assertThat(updatedItem.title).isEqualTo("Updated Title")
        assertThat(updatedItem.content).isEqualTo("Updated Content")
    }

    /**
     * Test updating a copy of the original entity, because this way it's ensured that the service can handle detached entities.
     */
    @Test
    fun `update chapter detached with copy()`() {
        // Arrange
        val item = Chapter(null, "Original Title", content = "Original Content")
        val savedItem = chapterService.add(item)

        // Act
        val retrievedItem = chapterService.get(savedItem.id!!)
        val updateItem = retrievedItem.copy(
            title = "Updated Title",
            content = "Updated Content"
        )
        val updatedItem = chapterService.update(updateItem.id!!, updateItem)

        // Assert
        assertThat(updatedItem.title).isEqualTo("Updated Title")
        assertThat(updatedItem.content).isEqualTo("Updated Content")
    }

    fun itemProvider() = ChapterTestDataProvider.itemProvider()
}