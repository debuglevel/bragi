package de.debuglevel.bragi.places

import de.debuglevel.bragi.entity.AddEntityRequest

data class AddPlaceRequest(
    var name: String
) : AddEntityRequest<Place> {
    override fun toEntity(): Place {
        return Place(
            id = null,
            name = this.name,
            aliases = listOf(),
            notes = "",
            picture = null
        )
    }
}