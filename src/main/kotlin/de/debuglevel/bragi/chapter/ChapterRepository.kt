package de.debuglevel.bragi.chapter

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.util.*

@Repository
interface ChapterRepository : CrudRepository<Chapter, UUID> {
    fun find(title: String): Chapter
}