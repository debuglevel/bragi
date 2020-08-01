package de.debuglevel.bragi.places

import de.debuglevel.bragi.entity.AddEntityResponse
import java.time.LocalDateTime
import java.util.*

data class AddPlaceResponse(
    override var id: UUID?,
    var name: String,
    override var createdOn: LocalDateTime,
    override var lastModified: LocalDateTime
) : AddEntityResponse {
    constructor(place: Place) : this(
        id = place.id,
        name = place.name,
        createdOn = place.createdOn,
        lastModified = place.lastModified
    )
}