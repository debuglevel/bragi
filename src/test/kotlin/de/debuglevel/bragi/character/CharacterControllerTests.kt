package de.debuglevel.bragi.character

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
class CharacterControllerTests {
    @Inject
    lateinit var server: EmbeddedServer

    @Inject
    @field:Client("/characters")
    lateinit var httpClient: HttpClient

    @ParameterizedTest
    @MethodSource("addItemRequestProvider")
    fun `add character`(addCharacterRequest: AddCharacterRequest) {
        // Arrange

        // Act
        val uri = UriBuilder.of("/").build()
        val addedItem = httpClient.toBlocking()
            .retrieve(HttpRequest.POST(uri, addCharacterRequest), AddCharacterResponse::class.java)

        // Assert
        Assertions.assertThat(addedItem.name).isEqualTo(addCharacterRequest.name)
    }

    @ParameterizedTest
    @MethodSource("addItemRequestProvider")
    fun `retrieve character`(addCharacterRequest: AddCharacterRequest) {
        // Arrange
        val addUri = UriBuilder.of("/").build()
        val addedItem = httpClient.toBlocking()
            .retrieve(HttpRequest.POST(addUri, addCharacterRequest), AddCharacterResponse::class.java)

        // Act
        val retrieveUri = UriBuilder.of("/{id}")
            .expand(mutableMapOf("id" to addedItem.id))
            .toString()
        val retrievedItem = httpClient.toBlocking()
            .retrieve(retrieveUri, AddCharacterResponse::class.java)

        // Assert
        Assertions.assertThat(retrievedItem.id).isEqualTo(addedItem.id)
        Assertions.assertThat(retrievedItem.name).isEqualTo(addedItem.name)
        Assertions.assertThat(retrievedItem).isEqualTo(addedItem)
    }

    fun addItemRequestProvider() = CharacterTestDataProvider.itemProvider()
        .map {
            AddCharacterRequest(it.name)
        }
}