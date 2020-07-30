package de.debuglevel.bragi.character

import de.debuglevel.bragi.entity.EntityRepository
import io.micronaut.data.annotation.Repository

@Repository
interface CharacterRepository : EntityRepository<Character> {
    fun find(name: String): Character
}