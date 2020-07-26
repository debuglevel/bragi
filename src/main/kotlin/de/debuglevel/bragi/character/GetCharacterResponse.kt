package de.debuglevel.bragi.character

import java.time.LocalDateTime
import java.util.*

data class GetCharacterResponse(
    var id: UUID?,
    var name: String,
    var notes: String,
    var createdOn: LocalDateTime,
    var lastModified: LocalDateTime
) {
    constructor(character: Character) : this(
        id = character.id,
        name = character.name,
        notes = character.notes,
        createdOn = character.createdOn,
        lastModified = character.lastModified
    )
}