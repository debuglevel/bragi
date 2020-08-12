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
    @MethodSource("itemRequestProvider")
    fun `add character`(character: Character) {
        // Arrange
        val addCharacterRequest = AddCharacterRequest(
            name = character.name
        )

        // Act
        val addUri = UriBuilder.of("/").build()
        val addedItem = httpClient.toBlocking()
            .retrieve(HttpRequest.POST(addUri, addCharacterRequest), AddCharacterResponse::class.java)

        // Assert
        Assertions.assertThat(addedItem.name).isEqualTo(addCharacterRequest.name)
    }

    @ParameterizedTest
    @MethodSource("itemRequestProvider")
    fun `get character`(character: Character) {
        // Arrange
        val addCharacterRequest = AddCharacterRequest(
            name = character.name
        )
        val addUri = UriBuilder.of("/").build()
        val addedItem = httpClient.toBlocking()
            .retrieve(HttpRequest.POST(addUri, addCharacterRequest), AddCharacterResponse::class.java)

        // Act
        val getUri = UriBuilder.of("/{id}")
            .expand(mutableMapOf("id" to addedItem.id))
            .toString()
        val getItem = httpClient.toBlocking()
            .retrieve(getUri, GetCharacterResponse::class.java)

        // Assert
        Assertions.assertThat(getItem.id).isEqualTo(addedItem.id)
        Assertions.assertThat(addedItem.name).isEqualTo(character.name)
        Assertions.assertThat(getItem.name).isEqualTo(character.name)
    }

    @ParameterizedTest
    @MethodSource("itemRequestProvider")
    fun `update character`(character: Character) {
        // Arrange
        val addCharacterRequest = AddCharacterRequest(
            name = character.name
        )
        val addUri = UriBuilder.of("/").build()
        val addedItem = httpClient.toBlocking()
            .retrieve(HttpRequest.POST(addUri, addCharacterRequest), AddCharacterResponse::class.java)
        val getUri = UriBuilder.of("/{id}")
            .expand(mutableMapOf("id" to addedItem.id))
            .toString()
        val getItem = httpClient.toBlocking()
            .retrieve(getUri, GetCharacterResponse::class.java)

        // Act
        val updateCharacterRequest = UpdateCharacterRequest(
            name = character.name,
            notes = character.notes,
            aliases = character.aliases,
            picture = "data:image/png;base64,${character.picture}"
        )
        val updateUri = UriBuilder.of("/{id}")
            .expand(mutableMapOf("id" to addedItem.id))
            .toString()
        val updatedItem = httpClient.toBlocking()
            .retrieve(HttpRequest.PUT(updateUri, updateCharacterRequest), UpdateCharacterResponse::class.java)
        val getUpdatedItem = httpClient.toBlocking()
            .retrieve(getUri, GetCharacterResponse::class.java)

        // Assert
        Assertions.assertThat(updatedItem.id).isEqualTo(addedItem.id)
        Assertions.assertThat(getUpdatedItem.id).isEqualTo(addedItem.id)
        Assertions.assertThat(updatedItem.name).isEqualTo(character.name)
        Assertions.assertThat(getUpdatedItem.name).isEqualTo(character.name)
        Assertions.assertThat(updatedItem.notes).isEqualTo(character.notes)
        Assertions.assertThat(getUpdatedItem.notes).isEqualTo(character.notes)
        Assertions.assertThat(updatedItem.picture).isEqualTo("data:image/png;base64,${character.picture}")
        Assertions.assertThat(getUpdatedItem.picture).isEqualTo("data:image/png;base64,${character.picture}")
        // containsAll() instead of containsExactly() because server may automatically add some initial aliases based on the name
        Assertions.assertThat(updatedItem.aliases).containsAll(character.aliases)
        Assertions.assertThat(getUpdatedItem.aliases).containsAll(character.aliases)
    }

    fun itemRequestProvider() = CharacterTestDataProvider.itemProvider()
}