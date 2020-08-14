package de.debuglevel.bragi.render

import de.debuglevel.bragi.chapter.ChapterService
import de.debuglevel.bragi.character.CharacterService
import de.debuglevel.bragi.places.PlaceService
import de.debuglevel.bragi.render.html.HtmlRenderer
import de.debuglevel.bragi.render.odt.OdtRenderer
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.swagger.v3.oas.annotations.tags.Tag
import mu.KotlinLogging

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/renderings")
@Tag(name = "renderings")
class RenderController(
    private val odtRenderer: OdtRenderer,
    private val htmlRenderer: HtmlRenderer,
    private val chapterService: ChapterService,
    private val characterService: CharacterService,
    private val placeService: PlaceService
) {
    private val logger = KotlinLogging.logger {}

    @Get("/html")
    @Produces(MediaType.TEXT_HTML)
    fun getHtml(): HttpResponse<String> {
        logger.debug("Called getHtml()")
        return try {
            val chapters = chapterService.list().toList()
            val characters = characterService.list().toList()
            val places = placeService.list().toList()
            val html = htmlRenderer.render(chapters, characters, places) as String

            HttpResponse.ok(html)
        } catch (e: Exception) {
            logger.error(e) { "Unhandled exception" }
            HttpResponse.serverError<String>()
        }
    }
}