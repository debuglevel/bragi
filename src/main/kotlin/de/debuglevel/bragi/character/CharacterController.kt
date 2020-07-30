package de.debuglevel.bragi.character

import de.debuglevel.bragi.entity.EntityService
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
@Controller("/characters")
@Tag(name = "characters")
class CharacterController(private val characterService: CharacterService) {
    private val logger = KotlinLogging.logger {}

    /**
     * Get all characters
     * @return All characters
     */
    @Get("/")
    fun getAll(): HttpResponse<Set<GetCharacterResponse>> {
        logger.debug("Called getAll()")
        return try {
            val characters = characterService.list()
            val characterResponse = characters
                .map { GetCharacterResponse(it) }
                .toSet()

            HttpResponse.ok(characterResponse)
        } catch (e: Exception) {
            logger.error(e) { "Unhandled exception" }
            HttpResponse.serverError<Set<GetCharacterResponse>>()
        }
    }

    /**
     * Get a character
     * @param uuid ID of the character
     * @return A character
     */
    @Get("/{uuid}")
    fun getOne(uuid: UUID): HttpResponse<GetCharacterResponse> {
        logger.debug("Called getOne($uuid)")
        return try {
            val character = characterService.get(uuid)
            HttpResponse.ok(GetCharacterResponse(character))
        } catch (e: EntityService.ItemNotFoundException) {
            HttpResponse.badRequest<GetCharacterResponse>()
        } catch (e: Exception) {
            logger.error(e) { "Unhandled exception" }
            HttpResponse.serverError<GetCharacterResponse>()
        }
    }

    /**
     * Create a character.
     * @return A character
     */
    @Post("/")
    fun postOne(addCharacterRequest: AddCharacterRequest): HttpResponse<AddCharacterResponse> {
        logger.debug("Called postOne($addCharacterRequest)")

        return try {
            val character = Character(
                id = null,
                name = addCharacterRequest.name,
                notes = ""
            )
            val addedCharacter = characterService.add(character)

            HttpResponse.created(AddCharacterResponse(addedCharacter))
        } catch (e: Exception) {
            logger.error(e) { "Unhandled exception" }
            HttpResponse.serverError<AddCharacterResponse>()
        }
    }

    /**
     * Update a character.
     * @param uuid ID of the character
     * @return The updated character
     */
    @Put("/{uuid}")
    fun putOne(uuid: UUID, updateCharacterRequest: UpdateCharacterRequest): HttpResponse<UpdateCharacterResponse> {
        logger.debug("Called putOne($uuid, $updateCharacterRequest)")

        return try {
            val character = Character(
                id = null,
                name = updateCharacterRequest.name,
                notes = updateCharacterRequest.notes
            )
            val updatedCharacter = characterService.update(uuid, character)

            HttpResponse.ok(UpdateCharacterResponse(updatedCharacter))
        } catch (e: EntityService.ItemNotFoundException) {
            HttpResponse.badRequest<UpdateCharacterResponse>()
        } catch (e: Exception) {
            logger.error(e) { "Unhandled exception" }
            HttpResponse.serverError<UpdateCharacterResponse>()
        }
    }
}