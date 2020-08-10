package de.debuglevel.bragi.character

import de.debuglevel.bragi.character.CharacterUtils.extractFirstName
import de.debuglevel.bragi.entity.EntityService
import de.debuglevel.bragi.picture.ImageService
import de.debuglevel.bragi.picture.PictureProvider
import de.debuglevel.bragi.suggestion.SuggestionProvider
import mu.KotlinLogging
import java.awt.Dimension
import java.util.*
import javax.inject.Singleton

@Singleton
class CharacterService(
    private val characterRepository: CharacterRepository,
    private val imageService: ImageService
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
        val firstNamePartOccurredInTextCharacters = allCharacters.filter { text.contains(extractFirstName(it.name)) }
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
            else -> throw PictureProvider.PictureNotFoundException(id)
        }

        logger.debug { "Got picture for id='$id'" }
        return byteArrayPicture
    }

    /**
     * Gets the picture if available. Resizes the picture if maxWidth or maxHeight is given while maintaining the aspect ratio.
     */
    fun getPicture(id: UUID, maxWidth: Int?, maxHeight: Int?): ByteArray {
        return if (maxHeight == null && maxWidth == null) {
            getPicture(id)
        } else {
            getResizedPicture(id, maxWidth = maxWidth, maxHeight = maxHeight)
        }
    }

    fun getResizedPicture(id: UUID, maxWidth: Int?, maxHeight: Int?): ByteArray {
        logger.debug { "Getting resized picture for id='$id' with maxWidth=$maxWidth, maxHeight=$maxHeight ..." }
        if (maxWidth == null && maxHeight == null) {
            throw IllegalArgumentException("maxWidth or maxHeight must not be null")
        }

        val bytes = this.getPicture(id)

        val image = imageService.createImageFromBytes(bytes)
        val scaledDimension = imageService.getScaledDimension(
            Dimension(image.width, image.height),
            Dimension(maxWidth ?: Int.MAX_VALUE, maxHeight ?: Int.MAX_VALUE)
        )
        val resizedImage = imageService.resizeImage(image, scaledDimension)
        val resizedBytes = imageService.createBytesFromImage(resizedImage)

        logger.debug { "Got resized picture for id='$id'" }
        return resizedBytes
    }
}