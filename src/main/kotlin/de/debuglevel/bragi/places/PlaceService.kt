package de.debuglevel.bragi.places

import de.debuglevel.bragi.entity.EntityService
import de.debuglevel.bragi.picture.PictureProvider
import de.debuglevel.bragi.picture.PictureService
import de.debuglevel.bragi.suggestion.SuggestionProvider
import mu.KotlinLogging
import java.util.*
import javax.inject.Singleton

@Singleton
class PlaceService(
    private val placeRepository: PlaceRepository,
    private val pictureService: PictureService
) : EntityService<Place>(placeRepository), SuggestionProvider<Place>, PictureProvider {
    private val logger = KotlinLogging.logger {}
    override val entityName = "place"

    override fun update(id: UUID, item: Place): Place {
        logger.debug { "Updating place '$item' with ID '$id'..." }

        // an object must be known to Hibernate (i.e. retrieved first) to get updated;
        // it would be a "detached entity" otherwise.
        val updatePlace = this.get(id).apply {
            name = item.name
            aliases = item.aliases
            notes = item.notes
            picture = item.picture
        }

        val updatedPlace = placeRepository.update(updatePlace)

        logger.debug { "Updated place: $updatedPlace with ID '$id'" }
        return updatedPlace
    }

    override fun getSuggested(text: String): Set<Place> {
        logger.debug { "Suggesting places for given text..." }

        val allPlaces = this.list()
        val nameOccurredInTextPlaces = allPlaces.filter { text.contains(it.name) }
        val aliasesOccurredInTextPlaces =
            allPlaces.filter { place -> place.aliases.any { alias -> text.contains(alias) } }

        val foundPlaces = nameOccurredInTextPlaces
            .union(aliasesOccurredInTextPlaces)

        logger.debug { "Suggested ${foundPlaces.size} places for given text: ${foundPlaces.joinToString(", ")}" }
        return foundPlaces
    }

    override fun getPicture(id: UUID, maxWidth: Int?, maxHeight: Int?): ByteArray {
        return if (maxHeight == null && maxWidth == null) {
            pictureService.getPicture(this.get(id))
        } else {
            pictureService.getResizedPicture(this.get(id), maxWidth = maxWidth, maxHeight = maxHeight)
        }
    }
}