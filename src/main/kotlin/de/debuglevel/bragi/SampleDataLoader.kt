package de.debuglevel.bragi

import de.debuglevel.bragi.chapter.Chapter
import de.debuglevel.bragi.chapter.ChapterService
import de.debuglevel.bragi.character.Character
import de.debuglevel.bragi.character.CharacterService
import io.micronaut.context.annotation.Requires
import io.micronaut.context.env.Environment
import io.micronaut.context.event.ApplicationEventListener
import io.micronaut.discovery.event.ServiceReadyEvent
import mu.KotlinLogging
import javax.inject.Singleton

/**
 * Populate the database with sample data during development.
 */
@Singleton
@Requires(notEnv = [Environment.TEST]) // Do not load data in tests.
class SampleDataLoader(
    private val characterService: CharacterService,
    private val chapterService: ChapterService
) : ApplicationEventListener<ServiceReadyEvent> {
    private val logger = KotlinLogging.logger {}

    override fun onApplicationEvent(event: ServiceReadyEvent) {
        logger.debug { "Populating database with sample data..." }

        val eddardStark = Character(
            id = null,
            name = "Eddard Stark",
            notes = "Really likable person. Might become king or something."
        )
        val aryaStark = Character(
            id = null,
            name = "Arya Stark",
            notes = "Cute little girl. Will never hurt anybody."
        )
        val sansaStark = Character(
            id = null,
            name = "Sansa Stark",
            notes = "Just an annoying supporting character. Probably ginger."
        )
        val jonSnow = Character(
            id = null,
            name = "Jon Snow",
            notes = "Way to good character. Kind of boring."
        )
        val joffreyBaratheon = Character(
            id = null,
            name = "Joffrey Baratheon",
            notes = "Ambitious likable fella."
        )
        val tyrionLannister = Character(
            id = null,
            name = "Tyrion Lannister",
            notes = "Actually the only respectable character in the whole story."
        )
        val samwellTarly = Character(
            id = null,
            name = "Samwell Tarly",
            notes = "Huge nerd."
        )

        characterService.add(eddardStark)
        characterService.add(aryaStark)
        characterService.add(sansaStark)
        characterService.add(jonSnow)
        characterService.add(joffreyBaratheon)
        characterService.add(tyrionLannister)
        characterService.add(samwellTarly)

        val bigBang = Chapter(
            id = null,
            title = "Big Bang",
            content = "Well, in the beginning there was nothing. And then the big bang happened. We do not really know why."
        )
        val expandingUniverse = Chapter(
            id = null,
            title = "Universe expands",
            content = "The universe started expanding (and still does)."
        )
        val coolDown = Chapter(
            id = null,
            title = "Matter cools down",
            content = "The freaking hot matter cools down slowly."
        )
        val matterGravitates = Chapter(
            id = null,
            title = "Matter gravitates towards another",
            content = "This way some stars and planets form (and galaxies)."
        )
        val unicellularLife = Chapter(
            id = null,
            title = "Simple life develops",
            content = "Unicellular organisms and stuff"
        )
        val humanLife = Chapter(
            id = null,
            title = "Human life develops",
            content = "Homo sapiens and stuff. Includes Arya and Jon Snow."
        )
        chapterService.add(bigBang)
        chapterService.add(expandingUniverse)
        chapterService.add(coolDown)
        chapterService.add(matterGravitates)
        chapterService.add(unicellularLife)
        chapterService.add(humanLife)

        logger.debug { "Populated database with sample data." }
    }
}