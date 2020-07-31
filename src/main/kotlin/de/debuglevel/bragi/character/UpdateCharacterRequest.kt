package de.debuglevel.bragi.character

import de.debuglevel.bragi.entity.UpdateEntityRequest

data class UpdateCharacterRequest(
    var name: String,
    var aliases: List<String>,
    var notes: String
) : UpdateEntityRequest<Character> {
    override fun toEntity(): Character {
        return Character(
            id = null,
            name = this.name,
            aliases = this.aliases,
            notes = this.notes
        )
    }
}