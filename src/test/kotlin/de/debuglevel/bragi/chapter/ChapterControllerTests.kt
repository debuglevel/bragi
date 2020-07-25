package de.debuglevel.bragi.chapter

import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.http.uri.UriBuilder
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.annotation.MicronautTest
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.Test
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
    @field:Client("/persons")
    lateinit var httpClient: HttpClient

    @ParameterizedTest
    @MethodSource("personRequestProvider")
    fun `save person`(addChapterRequest: AddChapterRequest) {
        // Arrange

        // Act
        val uri = UriBuilder.of("/").build()
        val savedPerson = httpClient.toBlocking()
            .retrieve(HttpRequest.POST(uri, addChapterRequest), UpdateChapterResponse::class.java)

        // Assert
        Assertions.assertThat(savedPerson.title).isEqualTo(addChapterRequest.title)
    }

    @ParameterizedTest
    @MethodSource("personRequestProvider")
    fun `retrieve person`(addChapterRequest: AddChapterRequest) {
        // Arrange
        val saveUri = UriBuilder.of("/").build()
        val savedPerson = httpClient.toBlocking()
            .retrieve(HttpRequest.POST(saveUri, addChapterRequest), AddChapterRequest::class.java)

        // Act
        val retrieveUri = UriBuilder.of("/{id}")
            .expand(mutableMapOf("id" to savedPerson.id))
            .toString()
        val retrievedPerson = httpClient.toBlocking()
            .retrieve(retrieveUri, AddChapterRequest::class.java)

        // Assert
        Assertions.assertThat(retrievedPerson.id).isEqualTo(savedPerson.id)
        Assertions.assertThat(retrievedPerson.title).isEqualTo(savedPerson.title)
        Assertions.assertThat(retrievedPerson).isEqualTo(savedPerson)
    }

    @Test
    fun `retrieve VIPs`() {
        // Arrange

        // Act
        val retrieveUri = UriBuilder.of("/VIPs").build()
        val httpRequest = HttpRequest
            .GET<String>(retrieveUri)
            .basicAuth("SECRET_USERNAME", "SECRET_PASSWORD")
        val argument = Argument.of(List::class.java, AddChapterRequest::class.java)
        val retrievedPersons = httpClient.toBlocking()
            .retrieve(httpRequest, argument) as List<AddChapterRequest>

        // Assert
        Assertions.assertThat(retrievedPersons).anyMatch { it.title == "Hermoine Granger" }
        Assertions.assertThat(retrievedPersons).anyMatch { it.title == "Harry Potter" }
        Assertions.assertThat(retrievedPersons).anyMatch { it.title == "Ronald Weasley" }
    }

    @Test
    fun `fail retrieving VIPs without authentication`() {
        // Arrange

        // Act
        val retrieveUri = UriBuilder.of("/VIPs").build()
        val httpRequest = HttpRequest
            .GET<String>(retrieveUri)
        val thrown = catchThrowable {
            httpClient.toBlocking().retrieve(httpRequest)
        }

        // Assert
        Assertions.assertThat(thrown)
            .isInstanceOf(HttpClientResponseException::class.java)
            .hasMessageContaining("Unauthorized")
    }

    fun personRequestProvider() = TestDataProvider.chapterProvider()
        .map {
            AddChapterRequest(it.id, it.title)
        }
}