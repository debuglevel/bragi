package de.debuglevel.bragi.chapter

import io.micronaut.test.annotation.MicronautTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import javax.inject.Inject

@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ChapterClientTests {

    @Inject
    lateinit var chapterClient: ChapterClient

    @ParameterizedTest
    @MethodSource("personRequestProvider")
    fun `save person`(addChapterRequest: AddChapterRequest) {
        // Arrange

        // Act
        val savedPerson = chapterClient.postOne(addChapterRequest).blockingGet()

        // Assert
        Assertions.assertThat(savedPerson.title).isEqualTo(addChapterRequest.title)
    }

    @ParameterizedTest
    @MethodSource("personRequestProvider")
    fun `retrieve person`(addChapterRequest: AddChapterRequest) {
        // Arrange
        val savedPerson = chapterClient.postOne(addChapterRequest).blockingGet()

        // Act
        val retrievedPerson = chapterClient.getOne(savedPerson.id!!).blockingGet()

        // Assert
        Assertions.assertThat(retrievedPerson.id).isEqualTo(savedPerson.id)
        Assertions.assertThat(retrievedPerson.title).isEqualTo(savedPerson.title)
        Assertions.assertThat(retrievedPerson).isEqualTo(savedPerson)
    }

    fun chapterRequestProvider() = TestDataProvider.chapterProvider()
        .map {
            AddChapterRequest(it.id, it.title)
        }
}