package de.debuglevel.bragi.places

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
}