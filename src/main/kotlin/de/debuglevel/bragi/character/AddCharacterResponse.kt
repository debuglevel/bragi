package de.debuglevel.bragi.character

import java.time.LocalDateTime
import java.util.*

data class AddCharacterResponse(
    var id: UUID?,
    var name: String,
    var createdOn: LocalDateTime,
    var lastModified: LocalDateTime
) {
    constructor(character: Character) : this(
        id = character.id,
        name = character.name,
        createdOn = character.createdOn,
        lastModified = character.lastModified
    )
}