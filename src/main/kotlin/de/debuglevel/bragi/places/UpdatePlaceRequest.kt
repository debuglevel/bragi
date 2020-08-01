package de.debuglevel.bragi.places

import de.debuglevel.bragi.entity.UpdateEntityRequest

data class UpdatePlaceRequest(
    var name: String,
    var aliases: List<String>,
    var notes: String
) : UpdateEntityRequest<Place> {
    override fun toEntity(): Place {
        return Place(
            id = null,
            name = this.name,
            aliases = this.aliases,
            notes = this.notes
        )
    }
}