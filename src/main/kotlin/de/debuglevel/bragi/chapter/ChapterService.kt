package de.debuglevel.bragi.chapter

import mu.KotlinLogging
import java.util.*
import javax.inject.Singleton

@Singleton
class ChapterService(
    private val chapterRepository: ChapterRepository
) {
    private val logger = KotlinLogging.logger {}

    fun get(id: UUID): Chapter {
        logger.debug { "Getting chapter with ID '$id'..." }

        val chapter: Chapter = chapterRepository.findById(id).orElseThrow {
            EntityNotFoundException(id)
        }

        logger.debug { "Got chapter with ID '$id': $chapter" }
        return chapter
    }

    fun add(chapter: Chapter): Chapter {
        logger.debug { "Saving chapter '$chapter'..." }

        val savedChapter = chapterRepository.save(chapter)

        logger.debug { "Saved chapter: $savedChapter" }
        return chapter
    }

    fun update(id: UUID, chapter: Chapter): Chapter {
        logger.debug { "Saving chapter '$chapter' with ID '$id'..." }

        // an object must be known to Hibernate (i.e. retrieved first) to get updated;
        // it would be a "detached entity" otherwise.
        val updateChapter = this.get(id).apply {
            title = chapter.title
            content = chapter.content
        }

        val savedChapter = chapterRepository.update(updateChapter)

        logger.debug { "Saved chapter: $savedChapter with ID '$id'" }
        return chapter
    }

    fun list(): Set<Chapter> {
        logger.debug { "Getting all chapters..." }

        val chapters = chapterRepository.findAll().toSet()

        logger.debug { "Got all chapters" }
        return chapters
    }

    class EntityNotFoundException(criteria: Any) : Exception("Entity '$criteria' does not exist.")
}