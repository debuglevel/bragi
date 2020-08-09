package de.debuglevel.bragi.character

import de.debuglevel.bragi.entity.EntityService
import de.debuglevel.bragi.picture.ImageService
import de.debuglevel.bragi.picture.PictureService
import de.debuglevel.bragi.suggestion.SuggestionService
import mu.KotlinLogging
import java.util.*
import javax.inject.Singleton

@Singleton
class CharacterService(
    private val characterRepository: CharacterRepository,
    private val imageService: ImageService
) : EntityService<Character>(characterRepository), SuggestionService<Character>, PictureService {
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

    // TODO: maybe move into Character class?
    private fun extractFirstName(character: Character): String {
        return character.name.split(" ").first()
    }

    override fun getSuggested(text: String): Set<Character> {
        logger.debug { "Suggesting characters for given text..." }

        val allCharacters = this.list()
        val nameOccurredInTextCharacters = allCharacters.filter { text.contains(it.name) }
        val firstNamePartOccurredInTextCharacters = allCharacters.filter { text.contains(extractFirstName(it)) }
        val aliasesOccurredInTextCharacters =
            allCharacters.filter { character -> character.aliases.any { alias -> text.contains(alias) } }

        val foundCharacters = nameOccurredInTextCharacters
            .union(firstNamePartOccurredInTextCharacters)
            .union(aliasesOccurredInTextCharacters)

        logger.debug { "Suggested ${foundCharacters.size} characters for given text: ${foundCharacters.joinToString(", ")}" }
        return foundCharacters
    }

    // TODO: refactor all this Image and Picture cruelty and make it available for Places, too.
    override fun getPicture(id: UUID): ByteArray {
        logger.debug { "Getting picture for id='$id'..." }

        val base64picture = this.get(id).picture
        val byteArrayPicture = when {
            !base64picture.isNullOrBlank() -> Base64.getDecoder().decode(base64picture)!!
            else -> throw PictureService.PictureNotFoundException(id)
        }

        logger.debug { "Got picture for id='$id'" }
        return byteArrayPicture
    }

    fun getPicture(id: UUID, width: Int?, height: Int?): ByteArray {
        return if (height == null || width == null) {
            getPicture(id)
        } else {
            getPicture(id, width, height)
        }
    }

    override fun getPicture(id: UUID, width: Int, height: Int): ByteArray {
        logger.debug { "Getting picture for id='$id' with maxWidth=$width, maxHeight=$height ..." }

        val base64picture = this.get(id).picture
        val byteArrayPicture = when {
            !base64picture.isNullOrBlank() -> Base64.getDecoder().decode(base64picture)!!
            else -> throw PictureService.PictureNotFoundException(id)
        }

        val bufferedImage = imageService.createImageFromBytes(byteArrayPicture)
        val resizedImage = imageService.resizeImage(bufferedImage, width, height)
        val resizedByteArrayPicture = imageService.createBytesFromImage(resizedImage)

        logger.debug { "Got picture for id='$id'" }
        return resizedByteArrayPicture
    }
}