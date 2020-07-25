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
    @MethodSource("personProvider")
    fun `save person`(chapter: Chapter) {
        // Arrange

        // Act
        val savedPerson = chapterService.add(chapter)

        // Assert
        assertThat(savedPerson).isEqualTo(chapter)
    }

    @ParameterizedTest
    @MethodSource("personProvider")
    fun `retrieve person`(chapter: Chapter) {
        // Arrange
        val savedPerson = chapterService.add(chapter)

        // Act
        val retrievedPerson = chapterService.get(savedPerson.id!!)

        // Assert
        assertThat(retrievedPerson).isEqualTo(savedPerson)
    }

    @Test
    fun `update person`() {
        // Arrange
        val person = Chapter(null, "Test")
        val savedPerson = chapterService.add(person)

        // Act
        val retrievedPerson = chapterService.get(savedPerson.id!!)
        retrievedPerson.title = "Test updated"
        val updatedPerson = chapterService.update(retrievedPerson.id!!, retrievedPerson)

        // Assert
        assertThat(updatedPerson.title).isEqualTo("Test updated")
    }

    /**
     * Test updating a copy of the original entity, because this way it's ensured that the service can handle detached entities.
     */
    @Test
    fun `update person with copy()`() {
        // Arrange
        val person = Chapter(null, "Test")
        val savedPerson = chapterService.add(person)

        // Act
        val retrievedPerson = chapterService.get(savedPerson.id!!)
        val updatePerson = retrievedPerson.copy(title = "Test updated")
        val updatedPerson = chapterService.update(updatePerson.id!!, updatePerson)

        // Assert
        assertThat(updatedPerson.title).isEqualTo("Test updated")
    }

    fun personProvider() = TestDataProvider.chapterProvider()
}