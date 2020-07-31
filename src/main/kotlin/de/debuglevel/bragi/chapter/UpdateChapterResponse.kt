package de.debuglevel.bragi.chapter

import de.debuglevel.bragi.entity.UpdateEntityResponse
import java.time.LocalDateTime
import java.util.*

data class UpdateChapterResponse(
    override var id: UUID?,
    var title: String,
    var content: String,
    var summary: String,
    var suggestedCharacters: MutableSet<UUID>,
    override var createdOn: LocalDateTime,
    override var lastModified: LocalDateTime
) : UpdateEntityResponse {
    constructor(chapter: Chapter) : this(
        id = chapter.id,
        title = chapter.title,
        content = chapter.content,
        summary = chapter.summary,
        suggestedCharacters = mutableSetOf<UUID>(),
        createdOn = chapter.createdOn,
        lastModified = chapter.lastModified
    )
}