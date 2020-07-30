package de.debuglevel.bragi.character

import de.debuglevel.bragi.entity.AddEntityResponse
import java.time.LocalDateTime
import java.util.*

data class AddCharacterResponse(
    override var id: UUID?,
    var name: String,
    override var createdOn: LocalDateTime,
    override var lastModified: LocalDateTime
) : AddEntityResponse {
    constructor(character: Character) : this(
        id = character.id,
        name = character.name,
        createdOn = character.createdOn,
        lastModified = character.lastModified
    )
}