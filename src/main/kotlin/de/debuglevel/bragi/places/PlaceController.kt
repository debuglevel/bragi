package de.debuglevel.bragi.places

import de.debuglevel.bragi.entity.*
import de.debuglevel.bragi.picture.PictureProvider
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.swagger.v3.oas.annotations.tags.Tag
import mu.KotlinLogging
import java.util.*

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/places")
@Tag(name = "places")
class PlaceController(
    private val placeService: PlaceService
) : EntityController<Place>(placeService) {
    private val logger = KotlinLogging.logger {}

    override fun createGetEntityResponse(entity: Place): GetEntityResponse {
        return GetPlaceResponse(entity)
    }

    override fun createUpdateEntityResponse(entity: Place): UpdateEntityResponse {
        return UpdatePlaceResponse(entity)
    }

    override fun createAddEntityResponse(entity: Place): AddEntityResponse {
        return AddPlaceResponse(entity)
    }

    /**
     * Create an item.
     * @return An item
     */
    @Post("/")
    fun postOne(addEntityRequest: AddPlaceRequest): HttpResponse<AddEntityResponse> {
        return postOneBase(addEntityRequest)
    }

    /**
     * Update an entity.
     * @param uuid ID of the entity
     * @return The updated entity
     */
    @Put("/{uuid}")
    fun putOne(uuid: UUID, updateEntityRequest: UpdatePlaceRequest): HttpResponse<UpdateEntityResponse> {
        return putOneBase(uuid, updateEntityRequest)
    }

    /**
     * Get picture
     * @param uuid ID of the item
     * @return An item
     */
    @Get("/{uuid}/picture{?maxWidth,maxHeight}")
    @Produces(MediaType.IMAGE_PNG)
    fun getPicture(uuid: UUID, maxWidth: Int?, maxHeight: Int?): HttpResponse<ByteArray> {
        logger.debug("Called getPicture($uuid)")
        return try {
            val picture = placeService.getPicture(uuid, maxWidth = maxWidth, maxHeight = maxHeight)
            HttpResponse.ok(picture)
        } catch (e: EntityService.ItemNotFoundException) {
            HttpResponse.badRequest<ByteArray>()
        } catch (e: PictureProvider.PictureNotFoundException) {
            HttpResponse.notFound<ByteArray>()
        } catch (e: Exception) {
            logger.error(e) { "Unhandled exception" }
            HttpResponse.serverError<ByteArray>()
        }
    }
}