package de.debuglevel.bragi.character

import java.util.stream.Stream

object CharacterTestDataProvider {
    fun itemProvider() = Stream.of(
        Character(
            id = null,
            name = "Character 1",
            notes = "LongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotes",
            aliases = mutableListOf()
        ),
        Character(
            id = null,
            name = "Character ÖÜÄß",
            notes = "Notes",
            aliases = mutableListOf()
        ),
        Character(
            id = null,
            name = "コハウプト マルク",
            notes = "",
            aliases = mutableListOf()
        )
    )
}