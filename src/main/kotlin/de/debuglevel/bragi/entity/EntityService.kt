package de.debuglevel.bragi.entity

import mu.KotlinLogging
import java.util.*

abstract class EntityService<T>(private val entityRepository: EntityRepository<T>) {
    private val logger = KotlinLogging.logger {}
    protected abstract val entityName: String

    fun get(id: UUID): T {
        logger.debug { "Getting $entityName with ID '$id'..." }

        val item: T = entityRepository.findById(id).orElseThrow {
            ItemNotFoundException(id)
        }

        logger.debug { "Got $entityName with ID '$id': $item" }
        return item
    }

    fun add(item: T): T {
        logger.debug { "Adding $entityName '$item'..." }

        val addedItem = entityRepository.save(item)

        logger.debug { "Added $entityName: $addedItem" }
        return addedItem
    }

    abstract fun update(id: UUID, item: T): T

    fun list(): Set<T> {
        logger.debug { "Getting all ${entityName}s..." }

        val items = entityRepository.findAll().toSet()

        logger.debug { "Got all ${entityName}s" }
        return items
    }

    class ItemNotFoundException(criteria: Any) : Exception("Item '$criteria' does not exist.")
}