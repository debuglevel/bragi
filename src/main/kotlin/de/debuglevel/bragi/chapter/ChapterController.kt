package de.debuglevel.bragi.chapter

import de.debuglevel.bragi.entity.AddEntityResponse
import de.debuglevel.bragi.entity.EntityController
import de.debuglevel.bragi.entity.GetEntityResponse
import de.debuglevel.bragi.entity.UpdateEntityResponse
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
        return postOneBase(addEntityRequest)
    }

    /**
     * Update an entity.
     * @param uuid ID of the entity
     * @return The updated entity
     */
    @Put("/{uuid}")
    fun putOne(uuid: UUID, updateEntityRequest: UpdateChapterRequest): HttpResponse<UpdateEntityResponse> {
        return putOneBase(uuid, updateEntityRequest)
    }
}