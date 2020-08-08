package de.debuglevel.bragi.character

import de.debuglevel.bragi.entity.AddEntityRequest

data class AddCharacterRequest(
    var name: String
) : AddEntityRequest<Character> {
    override fun toEntity(): Character {
        return Character(
            id = null,
            name = this.name,
            aliases = mutableListOf(),
            notes = "",
            picture = null
        )
    }
}