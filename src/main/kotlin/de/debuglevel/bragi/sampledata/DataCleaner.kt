package de.debuglevel.bragi.sampledata

import de.debuglevel.bragi.chapter.ChapterRepository
import de.debuglevel.bragi.character.CharacterRepository
import de.debuglevel.bragi.places.PlaceRepository
import javax.inject.Singleton

@Singleton
class DataCleaner(
    private val characterRepository: CharacterRepository,
    private val placeRepository: PlaceRepository,
    private val chapterRepository: ChapterRepository
) {
    fun clean() {
        characterRepository.findAll().forEach { characterRepository.delete(it) }
        placeRepository.findAll().forEach { placeRepository.delete(it) }
        chapterRepository.findAll().forEach { chapterRepository.delete(it) }
    }
}