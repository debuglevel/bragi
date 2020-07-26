package de.debuglevel.bragi.character

import java.util.*

data class AddCharacterResponse(
    var id: UUID?,
    var name: String
) {
    constructor(character: Character) : this(
        character.id,
        character.name
    )
}