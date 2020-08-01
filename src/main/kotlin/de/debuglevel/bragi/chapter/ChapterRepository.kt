package de.debuglevel.bragi.chapter

import de.debuglevel.bragi.entity.EntityRepository
import io.micronaut.data.annotation.Join
import io.micronaut.data.annotation.Repository

@Repository
interface ChapterRepository : EntityRepository<Chapter> {
    fun find(title: String): Chapter

    @Join(value = "characters", type = Join.Type.FETCH)
    fun list(): List<Chapter>
}