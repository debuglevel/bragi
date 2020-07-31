package de.debuglevel.bragi.chapter

import de.debuglevel.bragi.entity.UpdateEntityRequest

data class UpdateChapterRequest(
    var title: String,
    var content: String,
    var summary: String
) : UpdateEntityRequest<Chapter> {
    override fun toEntity(): Chapter {
        return Chapter(
            id = null,
            title = this.title,
            content = this.content,
            summary = this.summary
        )
    }
}