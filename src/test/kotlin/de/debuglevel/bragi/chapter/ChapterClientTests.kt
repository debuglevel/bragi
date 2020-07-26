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
    @MethodSource("addItemRequestProvider")
    fun `add chapter`(addChapterRequest: AddChapterRequest) {
        // Arrange

        // Act
        val addedItem = chapterClient.postOne(addChapterRequest).blockingGet()

        // Assert
        Assertions.assertThat(addedItem.title).isEqualTo(addChapterRequest.title)
    }

    @ParameterizedTest
    @MethodSource("addItemRequestProvider")
    fun `retrieve chapter`(addChapterRequest: AddChapterRequest) {
        // Arrange
        val addedItem = chapterClient.postOne(addChapterRequest).blockingGet()

        // Act
        val retrievedItem = chapterClient.getOne(addedItem.id!!).blockingGet()

        // Assert
        Assertions.assertThat(retrievedItem.id).isEqualTo(addedItem.id)
        Assertions.assertThat(retrievedItem.title).isEqualTo(addedItem.title)
    }

    fun addItemRequestProvider() = ChapterTestDataProvider.itemProvider()
        .map {
            AddChapterRequest(it.title)
        }
}