package de.debuglevel.bragi.chapter

import de.debuglevel.bragi.entity.AddEntityRequest

data class AddChapterRequest(
    var title: String
) : AddEntityRequest<Chapter> {
    override fun toEntity(): Chapter {
        return Chapter(
            id = null,
            title = this.title,
            content = "",
            summary = "",
            notes = ""
        )
    }
}