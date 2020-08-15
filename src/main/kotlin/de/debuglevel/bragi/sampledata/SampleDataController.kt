package de.debuglevel.bragi.sampledata

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.swagger.v3.oas.annotations.tags.Tag
import mu.KotlinLogging

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/sampledata")
@Tag(name = "sampledata")
class SampleDataController(
    private val gotDataLoader: GotDataLoader,
    private val book2GraphDataLoader: Book2GraphDataLoader
) {
    private val logger = KotlinLogging.logger {}

    @Get("/")
    fun getAll(): HttpResponse<Set<String>> {
        logger.debug("Called getAll()")
        return try {
            HttpResponse.ok(setOf("got", "book2graph"))
        } catch (e: Exception) {
            logger.error(e) { "Unhandled exception" }
            HttpResponse.serverError<Set<String>>()
        }
    }

    // TODO: is a GET for now, as it is simpler for accessing in a browser for testing; but should clearly be POST or PUT
    @Get("/got")
    fun getOneGot(): HttpResponse<String> {
        logger.debug("Called postOneGot()")

        return try {
            gotDataLoader.populate()
            HttpResponse.created("done")
        } catch (e: Exception) {
            logger.error(e) { "Unhandled exception" }
            HttpResponse.serverError<String>()
        }
    }

    // TODO: is a GET for now, as it is simpler for accessing in a browser for testing; but should clearly be POST or PUT
    @Get("/book2graph")
    fun getOneBook2Graph(): HttpResponse<String> {
        logger.debug("Called postOneBook2Graph()")

        return try {
            book2GraphDataLoader.populate()
            HttpResponse.created("done")
        } catch (e: Exception) {
            logger.error(e) { "Unhandled exception" }
            HttpResponse.serverError<String>()
        }
    }
}