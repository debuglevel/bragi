package de.debuglevel.bragi.character

import mu.KotlinLogging
import java.util.*
import javax.inject.Singleton

@Singleton
class CharacterService(
    private val characterRepository: CharacterRepository
) {
    private val logger = KotlinLogging.logger {}

    fun get(id: UUID): Character {
        logger.debug { "Getting character with ID '$id'..." }

        val character: Character = characterRepository.findById(id).orElseThrow {
            EntityNotFoundException(id)
        }

        logger.debug { "Got character with ID '$id': $character" }
        return character
    }

    fun add(character: Character): Character {
        logger.debug { "Adding character '$character'..." }

        val addedCharacter = characterRepository.save(character)

        logger.debug { "Added character: $addedCharacter" }
        return addedCharacter
    }

    fun update(id: UUID, character: Character): Character {
        logger.debug { "Updating character '$character' with ID '$id'..." }

        // an object must be known to Hibernate (i.e. retrieved first) to get updated;
        // it would be a "detached entity" otherwise.
        val updateCharacter = this.get(id).apply {
            name = character.name
            notes = character.notes
        }

        val updatedCharacter = characterRepository.update(updateCharacter)

        logger.debug { "Updated character: $updatedCharacter with ID '$id'" }
        return updatedCharacter
    }

    fun list(): Set<Character> {
        logger.debug { "Getting all characters..." }

        val characters = characterRepository.findAll().toSet()

        logger.debug { "Got all characters" }
        return characters
    }

    class EntityNotFoundException(criteria: Any) : Exception("Entity '$criteria' does not exist.")
}