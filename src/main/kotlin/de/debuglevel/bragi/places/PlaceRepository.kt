package de.debuglevel.bragi.places

import de.debuglevel.bragi.entity.EntityRepository
import io.micronaut.data.annotation.Repository

@Repository
interface PlaceRepository : EntityRepository<Place> {
    fun find(name: String): Place
}