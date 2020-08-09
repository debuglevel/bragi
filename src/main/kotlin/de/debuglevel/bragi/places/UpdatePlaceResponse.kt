package de.debuglevel.bragi.places

import de.debuglevel.bragi.entity.UpdateEntityResponse
import java.time.LocalDateTime
import java.util.*

data class UpdatePlaceResponse(
    override var id: UUID?,
    var name: String,
    var aliases: List<String>,
    var notes: String,
    var picture: String?,
    override var createdOn: LocalDateTime,
    override var lastModified: LocalDateTime
) : UpdateEntityResponse {
    constructor(place: Place) : this(
        id = place.id,
        name = place.name,
        aliases = place.aliases.toList(),
        notes = place.notes,
        picture = if (place.picture != null) { "data:image/png;base64,${place.picture}" } else { null },
        createdOn = place.createdOn,
        lastModified = place.lastModified
    )
}