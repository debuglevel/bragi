package de.debuglevel.bragi.chapter

import java.util.*

data class GetChapterResponse(
    var id: UUID?,
    var title: String,
    var content: String
) {
    constructor(chapter: Chapter) : this(
        id = chapter.id,
        title = chapter.title,
        content = chapter.content
    )
}