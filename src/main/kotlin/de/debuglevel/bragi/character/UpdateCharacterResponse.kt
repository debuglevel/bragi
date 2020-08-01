package de.debuglevel.bragi.character

import de.debuglevel.bragi.entity.UpdateEntityResponse
import java.time.LocalDateTime
import java.util.*

data class UpdateCharacterResponse(
    override var id: UUID?,
    var name: String,
    var aliases: List<String>,
    var notes: String,
    var chapters: MutableSet<UUID>,
    override var createdOn: LocalDateTime,
    override var lastModified: LocalDateTime
) : UpdateEntityResponse {
    constructor(character: Character) : this(
        id = character.id,
        name = character.name,
        aliases = character.aliases.toList(),
        notes = character.notes,
        chapters = mutableSetOf<UUID>(),
        createdOn = character.createdOn,
        lastModified = character.lastModified
    )
}