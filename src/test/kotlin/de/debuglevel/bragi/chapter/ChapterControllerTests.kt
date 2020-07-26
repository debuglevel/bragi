package de.debuglevel.bragi.chapter

import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.uri.UriBuilder
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.annotation.MicronautTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import javax.inject.Inject


@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ChapterControllerTests {
    @Inject
    lateinit var server: EmbeddedServer

    @Inject
    @field:Client("/chapters")
    lateinit var httpClient: HttpClient

    @ParameterizedTest
    @MethodSource("addItemRequestProvider")
    fun `add chapter`(addChapterRequest: AddChapterRequest) {
        // Arrange

        // Act
        val uri = UriBuilder.of("/").build()
        val addedItem = httpClient.toBlocking()
            .retrieve(HttpRequest.POST(uri, addChapterRequest), AddChapterResponse::class.java)

        // Assert
        Assertions.assertThat(addedItem.title).isEqualTo(addChapterRequest.title)
    }

    @ParameterizedTest
    @MethodSource("addItemRequestProvider")
    fun `retrieve chapter`(addChapterRequest: AddChapterRequest) {
        // Arrange
        val addUri = UriBuilder.of("/").build()
        val addedItem = httpClient.toBlocking()
            .retrieve(HttpRequest.POST(addUri, addChapterRequest), AddChapterResponse::class.java)

        // Act
        val retrieveUri = UriBuilder.of("/{id}")
            .expand(mutableMapOf("id" to addedItem.id))
            .toString()
        val retrievedItem = httpClient.toBlocking()
            .retrieve(retrieveUri, AddChapterResponse::class.java)

        // Assert
        Assertions.assertThat(retrievedItem.id).isEqualTo(addedItem.id)
        Assertions.assertThat(retrievedItem.title).isEqualTo(addedItem.title)
        Assertions.assertThat(retrievedItem).isEqualTo(addedItem)
    }

    fun addItemRequestProvider() = ChapterTestDataProvider.itemProvider()
        .map {
            AddChapterRequest(it.title)
        }
}