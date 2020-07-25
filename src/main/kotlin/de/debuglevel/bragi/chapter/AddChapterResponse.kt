package de.debuglevel.bragi.chapter

import java.util.*

data class AddChapterResponse(
    var id: UUID?,
    var title: String
) {
    constructor(chapter: Chapter) : this(
        chapter.id,
        chapter.title
    )
}