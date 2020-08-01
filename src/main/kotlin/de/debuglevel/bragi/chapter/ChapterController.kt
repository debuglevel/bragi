package de.debuglevel.bragi.chapter

import de.debuglevel.bragi.character.CharacterService
import de.debuglevel.bragi.entity.AddEntityResponse
import de.debuglevel.bragi.entity.EntityController
import de.debuglevel.bragi.entity.UpdateEntityResponse
import de.debuglevel.bragi.places.PlaceService
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
    private val chapterService: ChapterService,
    private val placeService: PlaceService,
    private val characterService: CharacterService
) : EntityController<Chapter>(chapterService) {
    private val logger = KotlinLogging.logger {}

    override fun createGetEntityResponse(entity: Chapter): GetChapterResponse {
        val suggestedCharacters = characterService.getSuggestedCharacters(entity.content)
        val suggestedPlaces = placeService.getSuggestedPlaces(entity.content)

        val getEntityResponse = GetChapterResponse(entity)
        getEntityResponse.suggestedCharacters.addAll(suggestedCharacters.map { it.id!! })
        getEntityResponse.suggestedPlaces.addAll(suggestedPlaces.map { it.id!! })

        return getEntityResponse
    }

    override fun createUpdateEntityResponse(entity: Chapter): UpdateChapterResponse {
        val suggestedCharacters = characterService.getSuggestedCharacters(entity.content)
        val suggestedPlaces = placeService.getSuggestedPlaces(entity.content)

        val getEntityResponse = UpdateChapterResponse(entity)
        getEntityResponse.suggestedCharacters.addAll(suggestedCharacters.map { it.id!! })
        getEntityResponse.suggestedPlaces.addAll(suggestedPlaces.map { it.id!! })

        return getEntityResponse
    }

    override fun createAddEntityResponse(entity: Chapter): AddChapterResponse {
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