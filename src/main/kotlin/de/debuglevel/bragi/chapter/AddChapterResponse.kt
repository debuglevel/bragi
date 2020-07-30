package de.debuglevel.bragi.chapter

import de.debuglevel.bragi.entity.AddEntityResponse
import java.time.LocalDateTime
import java.util.*

data class AddChapterResponse(
    override var id: UUID?,
    var title: String,
    override var createdOn: LocalDateTime,
    override var lastModified: LocalDateTime
) : AddEntityResponse {
    constructor(chapter: Chapter) : this(
        id = chapter.id,
        title = chapter.title,
        createdOn = chapter.createdOn,
        lastModified = chapter.lastModified
    )
}