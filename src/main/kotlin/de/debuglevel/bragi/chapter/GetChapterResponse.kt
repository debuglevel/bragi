package de.debuglevel.bragi.chapter

import de.debuglevel.bragi.entity.GetEntityResponse
import java.time.LocalDateTime
import java.util.*

data class GetChapterResponse(
    override var id: UUID?,
    var title: String,
    var content: String,
    var summary: String,
    var notes: String,
    var suggestedCharacters: MutableSet<UUID>,
    var characters: MutableSet<UUID>,
    var suggestedPlaces: MutableSet<UUID>,
    override var createdOn: LocalDateTime,
    override var lastModified: LocalDateTime
) : GetEntityResponse {
    constructor(chapter: Chapter) : this(
        id = chapter.id,
        title = chapter.title,
        content = chapter.content,
        summary = chapter.summary,
        notes = chapter.notes,
        suggestedCharacters = mutableSetOf<UUID>(),
        characters = chapter.characters.map { it.id!! }.toMutableSet(),
        suggestedPlaces = mutableSetOf<UUID>(),
        createdOn = chapter.createdOn,
        lastModified = chapter.lastModified
    )
}