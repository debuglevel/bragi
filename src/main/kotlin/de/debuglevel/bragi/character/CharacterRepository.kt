package de.debuglevel.bragi.character

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.util.*

@Repository
interface CharacterRepository : CrudRepository<Character, UUID> {
    fun find(name: String): Character
}