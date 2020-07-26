package de.debuglevel.bragi.chapter

import java.time.LocalDateTime
import java.util.*

data class AddChapterResponse(
    var id: UUID?,
    var title: String,
    var createdOn: LocalDateTime,
    var lastModified: LocalDateTime
) {
    constructor(chapter: Chapter) : this(
        id = chapter.id,
        title = chapter.title,
        createdOn = chapter.createdOn,
        lastModified = chapter.lastModified
    )
}