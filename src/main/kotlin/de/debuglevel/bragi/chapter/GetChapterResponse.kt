package de.debuglevel.bragi.chapter

import de.debuglevel.bragi.entity.GetEntityResponse
import java.time.LocalDateTime
import java.util.*

data class GetChapterResponse(
    override var id: UUID?,
    var title: String,
    var content: String,
    override var createdOn: LocalDateTime,
    override var lastModified: LocalDateTime
) : GetEntityResponse {
    constructor(chapter: Chapter) : this(
        id = chapter.id,
        title = chapter.title,
        content = chapter.content,
        createdOn = chapter.createdOn,
        lastModified = chapter.lastModified
    )
}