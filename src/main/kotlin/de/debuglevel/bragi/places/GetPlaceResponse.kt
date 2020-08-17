package de.debuglevel.bragi.places

import de.debuglevel.bragi.entity.GetEntityResponse
import java.time.LocalDateTime
import java.util.*

data class GetPlaceResponse(
    override var id: UUID?,
    var name: String,
    var aliases: List<String>,
    var notes: String,
    var picture: String?,
    override var createdOn: LocalDateTime,
    override var lastModified: LocalDateTime
) : GetEntityResponse {
    constructor(place: Place) : this(
        id = place.id,
        name = place.name,
        aliases = place.aliases.toList(),
        notes = place.notes,
        picture = if (place.picture != null) { "/places/${place.id}/picture" } else { null },
        createdOn = place.createdOn,
        lastModified = place.lastModified
    )
}