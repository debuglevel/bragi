package de.debuglevel.bragi.character

import de.debuglevel.bragi.entity.GetEntityResponse
import java.time.LocalDateTime
import java.util.*

data class GetCharacterResponse(
    override var id: UUID?,
    var name: String,
    var aliases: List<String>,
    var notes: String,
    var picture: String?,
    override var createdOn: LocalDateTime,
    override var lastModified: LocalDateTime
) : GetEntityResponse {
    constructor(character: Character) : this(
        id = character.id,
        name = character.name,
        aliases = character.aliases.toList(),
        notes = character.notes,
        picture = if (character.picture != null) { "/characters/${character.id}/picture" } else { null },
        createdOn = character.createdOn,
        lastModified = character.lastModified
    )
}