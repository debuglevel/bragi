package de.debuglevel.bragi.character

import de.debuglevel.bragi.entity.EntityService
import de.debuglevel.bragi.picture.PictureProvider
import de.debuglevel.bragi.picture.PictureService
import de.debuglevel.bragi.suggestion.SuggestionProvider
import mu.KotlinLogging
import java.util.*
import javax.inject.Singleton

@Singleton
class CharacterService(
    private val characterRepository: CharacterRepository,
    private val pictureService: PictureService
) : EntityService<Character>(characterRepository), SuggestionProvider<Character>, PictureProvider {
    private val logger = KotlinLogging.logger {}
    override val entityName = "character"

    override fun update(id: UUID, item: Character): Character {
        logger.debug { "Updating character '$item' with ID '$id'..." }

        // an object must be known to Hibernate (i.e. retrieved first) to get updated;
        // it would be a "detached entity" otherwise.
        val updateCharacter = this.get(id).apply {
            name = item.name
            aliases = item.aliases
            notes = item.notes
            picture = item.picture
        }

        val updatedCharacter = characterRepository.update(updateCharacter)

        logger.debug { "Updated character: $updatedCharacter with ID '$id'" }
        return updatedCharacter
    }

    override fun getSuggested(text: String): Set<Character> {
        logger.debug { "Suggesting characters for given text..." }

        val allCharacters = this.list()
        val nameOccurredInTextCharacters = allCharacters.filter { text.contains(it.name) }
        val aliasesOccurredInTextCharacters =
            allCharacters.filter { character -> character.aliases.any { alias -> text.contains(alias) } }

        val foundCharacters = nameOccurredInTextCharacters
            .union(aliasesOccurredInTextCharacters)

        logger.debug { "Suggested ${foundCharacters.size} characters for given text: ${foundCharacters.joinToString(", ")}" }
        return foundCharacters
    }

    override fun getPicture(id: UUID, maxWidth: Int?, maxHeight: Int?): ByteArray {
        return if (maxHeight == null && maxWidth == null) {
            pictureService.getPicture(this.get(id))
        } else {
            pictureService.getResizedPicture(this.get(id), maxWidth = maxWidth, maxHeight = maxHeight)
        }
    }
}