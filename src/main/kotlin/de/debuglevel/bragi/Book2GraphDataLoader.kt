package de.debuglevel.bragi

import de.debuglevel.book2graph.parser.xml.FodtParser
import de.debuglevel.bragi.chapter.Chapter
import de.debuglevel.bragi.chapter.ChapterService
import de.debuglevel.bragi.character.Character
import de.debuglevel.bragi.character.CharacterService
import de.debuglevel.bragi.places.Place
import de.debuglevel.bragi.places.PlaceService
import io.micronaut.context.annotation.Requires
import io.micronaut.context.env.Environment
import io.micronaut.context.event.ApplicationEventListener
import io.micronaut.discovery.event.ServiceReadyEvent
import mu.KotlinLogging
import java.io.File
import javax.inject.Singleton

/**
 * Populate the database with sample data imported from Book2Graph.
 */
@Singleton
@Requires(notEnv = [Environment.TEST]) // Do not load data in tests.
class Book2GraphDataLoader(
    private val characterService: CharacterService,
    private val placeService: PlaceService,
    private val chapterService: ChapterService
) : ApplicationEventListener<ServiceReadyEvent> {
    private val logger = KotlinLogging.logger {}

    override fun onApplicationEvent(event: ServiceReadyEvent) {
        //return // remove if you've got the necessary data ;)
        logger.debug { "Populating database with sample data..." }

        val mrPreston = Character(
            id = null,
            name = "Mr. Preston",
            aliases = listOf("Preston"),
            notes = ""
        )
        val mrsPreston = Character(
            id = null,
            name = "Mrs. Preston",
            aliases = listOf(),
            notes = ""
        )
        val iraPreston = Character(
            id = null,
            name = "Ira Preston",
            aliases = listOf("Ira"),
            notes = ""
        )
        val charly = Character(
            id = null,
            name = "Charly",
            aliases = listOf(),
            notes = ""
        )
        val kayne = Character(
            id = null,
            name = "Kayne",
            aliases = listOf(),
            notes = ""
        )
        val sandra = Character(
            id = null,
            name = "Sandra",
            aliases = listOf("San"),
            notes = ""
        )
        val sirrus = Character(
            id = null,
            name = "Sirrus",
            aliases = listOf(),
            notes = ""
        )
        val joey = Character(
            id = null,
            name = "Joey",
            aliases = listOf(),
            notes = ""
        )
        val amrai = Character(
            id = null,
            name = "Amrai",
            aliases = listOf(),
            notes = ""
        )

        characterService.add(mrPreston)
        characterService.add(mrsPreston)
        characterService.add(iraPreston)
        characterService.add(charly)
        characterService.add(kayne)
        characterService.add(sandra)
        characterService.add(sirrus)
        characterService.add(joey)
        characterService.add(amrai)

        val plasa = Place(
            id = null,
            name = "Die Plasa",
            aliases = listOf("Plasa"),
            notes = ""
        )
        val komplex = Place(
            id = null,
            name = "Der Komplex",
            aliases = listOf("Komplex"),
            notes = ""
        )
        val ebeneNull = Place(
            id = null,
            name = "Ebene Null",
            aliases = listOf(),
            notes = ""
        )
        placeService.add(plasa)
        placeService.add(komplex)
        placeService.add(ebeneNull)

        val book = FodtParser.parse(File("Buch.fodt"))
        val chapters = book.chapters.map {
            Chapter(
                id = null,
                title = it.title,
                content = it.textAsString,
                summary = it.summaryAsString,
                notes = it.commentAsString
            )
        }
        chapters.forEach { chapterService.add(it) }

        logger.debug { "Populated database with sample data." }
    }
}