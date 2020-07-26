package de.debuglevel.bragi.chapter

import java.time.LocalDateTime
import java.util.*

data class GetChapterResponse(
    var id: UUID?,
    var title: String,
    var content: String,
    var createdOn: LocalDateTime,
    var lastModified: LocalDateTime
) {
    constructor(chapter: Chapter) : this(
        id = chapter.id,
        title = chapter.title,
        content = chapter.content,
        createdOn = chapter.createdOn,
        lastModified = chapter.lastModified
    )
}