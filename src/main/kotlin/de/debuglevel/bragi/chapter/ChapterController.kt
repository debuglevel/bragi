package de.debuglevel.bragi.chapter

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.swagger.v3.oas.annotations.tags.Tag
import mu.KotlinLogging
import java.util.*

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/chapters")
@Tag(name = "chapters")
class ChapterController(private val chapterService: ChapterService) {
    private val logger = KotlinLogging.logger {}

    /**
     * Get all chapters
     * @return All chapters
     */
    @Get("/")
    fun getAll(): HttpResponse<Set<GetChapterResponse>> {
        logger.debug("Called getAll()")
        return try {
            val chapters = chapterService.list()
            val chapterResponse = chapters
                .map { GetChapterResponse(it) }
                .toSet()

            HttpResponse.ok(chapterResponse)
        } catch (e: Exception) {
            logger.error(e) { "Unhandled exception" }
            HttpResponse.serverError<Set<GetChapterResponse>>()
        }
    }

    /**
     * Get a chapter
     * @param uuid ID of the chapter
     * @return A chapter
     */
    @Get("/{uuid}")
    fun getOne(uuid: UUID): HttpResponse<GetChapterResponse> {
        logger.debug("Called getOne($uuid)")
        return try {
            val chapter = chapterService.get(uuid)
            HttpResponse.ok(GetChapterResponse(chapter))
        } catch (e: ChapterService.EntityNotFoundException) {
            HttpResponse.badRequest<GetChapterResponse>()
        } catch (e: Exception) {
            logger.error(e) { "Unhandled exception" }
            HttpResponse.serverError<GetChapterResponse>()
        }
    }

    /**
     * Create a chapter.
     * @return A chapter
     */
    @Post("/")
    fun postOne(addChapterRequest: AddChapterRequest): HttpResponse<AddChapterResponse> {
        logger.debug("Called postOne($addChapterRequest)")

        return try {
            val chapter = Chapter(
                id = null,
                title = addChapterRequest.title,
                content = ""
            )
            val addedChapter = chapterService.add(chapter)

            HttpResponse.created(AddChapterResponse(addedChapter))
        } catch (e: Exception) {
            logger.error(e) { "Unhandled exception" }
            HttpResponse.serverError<AddChapterResponse>()
        }
    }

    /**
     * Update a chapter.
     * @param uuid ID of the chapter
     * @return The updated chapter
     */
    @Put("/{uuid}")
    fun putOne(uuid: UUID, updateChapterRequest: UpdateChapterRequest): HttpResponse<UpdateChapterResponse> {
        logger.debug("Called putOne($uuid, $updateChapterRequest)")

        return try {
            val chapter = Chapter(
                id = null,
                title = updateChapterRequest.title,
                content = updateChapterRequest.content
            )
            val updatedChapter = chapterService.update(uuid, chapter)

            HttpResponse.ok(UpdateChapterResponse(updatedChapter))
        } catch (e: ChapterService.EntityNotFoundException) {
            HttpResponse.badRequest<UpdateChapterResponse>()
        } catch (e: Exception) {
            logger.error(e) { "Unhandled exception" }
            HttpResponse.serverError<UpdateChapterResponse>()
        }
    }
}