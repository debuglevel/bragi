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
        return // remove if you've got the necessary data ;)
        logger.debug { "Populating database with sample data..." }

        val mrPreston = Character(
            id = null,
            name = "Mr. Preston",
            aliases = mutableListOf("Preston", "Senator Preston"),
            notes = ""
        )
        val mrsPreston = Character(
            id = null,
            name = "Mrs. Preston",
            aliases = mutableListOf(),
            notes = ""
        )
        val iraPreston = Character(
            id = null,
            name = "Ira Preston",
            aliases = mutableListOf("Ira"),
            notes = ""
        )
        val charly = Character(
            id = null,
            name = "Charly",
            aliases = mutableListOf(),
            notes = ""
        )
        val kayne = Character(
            id = null,
            name = "Kayne",
            aliases = mutableListOf(),
            notes = ""
        )
        val sandra = Character(
            id = null,
            name = "Sandra",
            aliases = mutableListOf("San"),
            notes = ""
        )
        val sirrus = Character(
            id = null,
            name = "Sirrus",
            aliases = mutableListOf(),
            notes = ""
        )
        val joey = Character(
            id = null,
            name = "Joey",
            aliases = mutableListOf(),
            notes = ""
        )
        val amrai = Character(
            id = null,
            name = "Amrai",
            aliases = mutableListOf(),
            notes = ""
        )
        val dave = Character(
            id = null,
            name = "Dave",
            aliases = mutableListOf(),
            notes = ""
        )
        val floyd = Character(
            id = null,
            name = "Floyd",
            aliases = mutableListOf(),
            notes = ""
        )
        val edward = Character(
            id = null,
            name = "Edward",
            aliases = mutableListOf(),
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
        characterService.add(dave)
        characterService.add(floyd)
        characterService.add(edward)

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
        val evil = Place(
            id = null,
            name = "Evil",
            aliases = listOf(),
            notes = ""
        )
        val stMarysHospital = Place(
            id = null,
            name = "St. Mary's Hospital",
            aliases = listOf("St. Mary's", "Mary's"),
            notes = ""
        )
        val aufzug = Place(
            id = null,
            name = "Aufzug",
            aliases = listOf(),
            notes = ""
        )
        val skt = Place(
            id = null,
            name = "SKT",
            aliases = listOf("Shared Knowledge Technology", "Shared-Knowledge-Technology"),
            notes = ""
        )
        val landhaus = Place(
            id = null,
            name = "Landhaus",
            aliases = listOf(),
            notes = ""
        )
        val funkhaus = Place(
            id = null,
            name = "Funkhaus",
            aliases = listOf(),
            notes = ""
        )
        val auffanglager = Place(
            id = null,
            name = "Auffanglager",
            aliases = listOf(),
            notes = ""
        )
        val schreibzimmer = Place(
            id = null,
            name = "Schreibzimmer",
            aliases = listOf(),
            notes = ""
        )
        val ministerium = Place(
            id = null,
            name = "Ministerium",
            aliases = listOf(),
            notes = ""
        )
        val krankenfluegel = Place(
            id = null,
            name = "Krankenflügel",
            aliases = listOf(),
            notes = ""
        )
        placeService.add(plasa)
        placeService.add(komplex)
        placeService.add(ebeneNull)
        placeService.add(evil)
        placeService.add(stMarysHospital)
        placeService.add(aufzug)
        placeService.add(skt)
        placeService.add(landhaus)
        placeService.add(funkhaus)
        placeService.add(auffanglager)
        placeService.add(schreibzimmer)
        placeService.add(ministerium)
        placeService.add(krankenfluegel)

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