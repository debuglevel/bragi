package de.debuglevel.bragi.chapter

import de.debuglevel.bragi.entity.*
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
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
class ChapterController(
    private val chapterService: ChapterService
) : EntityController<Chapter>(chapterService) {
    private val logger = KotlinLogging.logger {}

    override fun createGetEntityResponse(entity: Chapter): GetEntityResponse {
        return GetChapterResponse(entity)
    }

    override fun createUpdateEntityResponse(entity: Chapter): UpdateEntityResponse {
        return UpdateChapterResponse(entity)
    }

    override fun createAddEntityResponse(entity: Chapter): AddEntityResponse {
        return AddChapterResponse(entity)
    }

    /**
     * Create an item.
     * @return An item
     */
    @Post("/")
    fun postOne(addEntityRequest: AddChapterRequest): HttpResponse<AddEntityResponse> {
        logger.debug("Called postOne($addEntityRequest)")

        return try {
            val item = addEntityRequest.toEntity()
            val addedItem = chapterService.add(item)

            HttpResponse.created(createAddEntityResponse(addedItem))
        } catch (e: Exception) {
            logger.error(e) { "Unhandled exception" }
            HttpResponse.serverError<AddEntityResponse>()
        }
    }

    /**
     * Update an entity.
     * @param uuid ID of the entity
     * @return The updated entity
     */
    @Put("/{uuid}")
    fun putOne(uuid: UUID, updateEntityRequest: UpdateChapterRequest): HttpResponse<UpdateEntityResponse> {
        logger.debug("Called putOne($uuid, $updateEntityRequest)")

        return try {
            val item = updateEntityRequest.toEntity()
            val updatedItem = chapterService.update(uuid, item)

            HttpResponse.ok(createUpdateEntityResponse(updatedItem))
        } catch (e: EntityService.ItemNotFoundException) {
            HttpResponse.badRequest<UpdateEntityResponse>()
        } catch (e: Exception) {
            logger.error(e) { "Unhandled exception" }
            HttpResponse.serverError<UpdateEntityResponse>()
        }
    }
}