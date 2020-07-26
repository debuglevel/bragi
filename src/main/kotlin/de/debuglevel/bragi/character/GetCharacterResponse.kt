package de.debuglevel.bragi.character

import java.util.*

data class GetCharacterResponse(
    var id: UUID?,
    var name: String,
    var notes: String
) {
    constructor(character: Character) : this(
        id = character.id,
        name = character.name,
        notes = character.notes
    )
}