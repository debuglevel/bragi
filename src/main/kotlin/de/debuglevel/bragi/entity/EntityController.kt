package de.debuglevel.bragi.entity

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import mu.KotlinLogging
import java.util.*

abstract class EntityController<T>(private val entityService: EntityService<T>) {
    private val logger = KotlinLogging.logger {}

    abstract fun createGetEntityResponse(entity: T): GetEntityResponse
    abstract fun createUpdateEntityResponse(entity: T): UpdateEntityResponse
    abstract fun createAddEntityResponse(entity: T): AddEntityResponse

    /**
     * Get all items
     * @return All items
     */
    @Get("/")
    fun getAll(): HttpResponse<Set<GetEntityResponse>> {
        logger.debug("Called getAll()")
        return try {
            val items = entityService.list()
            val itemResponse = items
                .map {
                    createGetEntityResponse(it)
                }
                .toSet()

            HttpResponse.ok(itemResponse)
        } catch (e: Exception) {
            logger.error(e) { "Unhandled exception" }
            HttpResponse.serverError<Set<GetEntityResponse>>()
        }
    }

    /**
     * Get an item
     * @param uuid ID of the item
     * @return An item
     */
    @Get("/{uuid}")
    open fun getOne(uuid: UUID): HttpResponse<GetEntityResponse> {
        logger.debug("Called getOne($uuid)")
        return try {
            val item = entityService.get(uuid)
            HttpResponse.ok(createGetEntityResponse(item))
        } catch (e: EntityService.ItemNotFoundException) {
            HttpResponse.badRequest<GetEntityResponse>()
        } catch (e: Exception) {
            logger.error(e) { "Unhandled exception" }
            HttpResponse.serverError<GetEntityResponse>()
        }
    }

    fun postOneBase(addEntityRequest: AddEntityRequest<T>): HttpResponse<AddEntityResponse> {
        logger.debug("Called postOne($addEntityRequest)")

        return try {
            val item = addEntityRequest.toEntity()
            val addedItem = entityService.add(item)

            HttpResponse.created(createAddEntityResponse(addedItem))
        } catch (e: Exception) {
            logger.error(e) { "Unhandled exception" }
            HttpResponse.serverError<AddEntityResponse>()
        }
    }

    fun putOneBase(uuid: UUID, updateEntityRequest: UpdateEntityRequest<T>): HttpResponse<UpdateEntityResponse> {
        logger.debug("Called putOne($uuid, $updateEntityRequest)")

        return try {
            val item = updateEntityRequest.toEntity()
            val updatedItem = entityService.update(uuid, item)

            HttpResponse.ok(createUpdateEntityResponse(updatedItem))
        } catch (e: EntityService.ItemNotFoundException) {
            HttpResponse.badRequest<UpdateEntityResponse>()
        } catch (e: Exception) {
            logger.error(e) { "Unhandled exception" }
            HttpResponse.serverError<UpdateEntityResponse>()
        }
    }
}