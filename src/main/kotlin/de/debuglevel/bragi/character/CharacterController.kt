package de.debuglevel.bragi.character

import de.debuglevel.bragi.entity.*
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.swagger.v3.oas.annotations.tags.Tag
import mu.KotlinLogging
import java.util.*

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/characters")
@Tag(name = "characters")
class CharacterController(
    private val characterService: CharacterService
) : EntityController<Character>(characterService) {
    private val logger = KotlinLogging.logger {}

    override fun createGetEntityResponse(entity: Character): GetEntityResponse {
        return GetCharacterResponse(entity)
    }

    override fun createUpdateEntityResponse(entity: Character): UpdateEntityResponse {
        return UpdateCharacterResponse(entity)
    }

    override fun createAddEntityResponse(entity: Character): AddEntityResponse {
        return AddCharacterResponse(entity)
    }

    /**
     * Create an item.
     * @return An item
     */
    @Post("/")
    fun postOne(addEntityRequest: AddCharacterRequest): HttpResponse<AddEntityResponse> {
        return postOneBase(addEntityRequest)
    }

    /**
     * Update an entity.
     * @param uuid ID of the entity
     * @return The updated entity
     */
    @Put("/{uuid}")
    fun putOne(uuid: UUID, updateEntityRequest: UpdateCharacterRequest): HttpResponse<UpdateEntityResponse> {
        return putOneBase(uuid, updateEntityRequest)
    }

    /**
     * Get picture
     * @param uuid ID of the item
     * @return An item
     */
    @Get("/{uuid}/picture")
    @Produces(MediaType.IMAGE_PNG)
    fun getPicture(uuid: UUID): HttpResponse<ByteArray> {
        logger.debug("Called getPicture($uuid)")
        return try {
            val picture = characterService.getPicture(uuid)
            HttpResponse.ok(picture)
        } catch (e: EntityService.ItemNotFoundException) {
            HttpResponse.badRequest<ByteArray>()
        } catch (e: Exception) {
            logger.error(e) { "Unhandled exception" }
            HttpResponse.serverError<ByteArray>()
        }
    }
}