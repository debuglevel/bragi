package de.debuglevel.bragi.character

import de.debuglevel.bragi.DataUrlUtils
import de.debuglevel.bragi.entity.UpdateEntityRequest

data class UpdateCharacterRequest(
    var name: String,
    var aliases: MutableList<String>,
    var notes: String,
    var picture: String?
) : UpdateEntityRequest<Character> {
    override fun toEntity(): Character {
        return Character(
            id = null,
            name = this.name,
            aliases = this.aliases,
            notes = this.notes,
            picture = if (!this.picture.isNullOrBlank()) {
                DataUrlUtils.getBase64Part(this.picture)
            } else {
                null
            }
        )
    }
}