package de.debuglevel.bragi.chapter

import de.debuglevel.bragi.entity.EntityService
import mu.KotlinLogging
import java.util.*
import javax.inject.Singleton

@Singleton
class ChapterService(
    private val chapterRepository: ChapterRepository
) : EntityService<Chapter>(chapterRepository) {
    private val logger = KotlinLogging.logger {}
    override val entityName = "chapter"

    override fun update(id: UUID, item: Chapter): Chapter {
        logger.debug { "Updating chapter '$item' with ID '$id'..." }

        // an object must be known to Hibernate (i.e. retrieved first) to get updated;
        // it would be a "detached entity" otherwise.
        val updateChapter = this.get(id).apply {
            title = item.title
            content = item.content
            summary = item.summary
        }

        val updatedChapter = chapterRepository.update(updateChapter)

        logger.debug { "Updated chapter: $updatedChapter with ID '$id'" }
        return updatedChapter
    }
}