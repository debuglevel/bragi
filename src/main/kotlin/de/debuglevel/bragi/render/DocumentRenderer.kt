package de.debuglevel.bragi.render

import de.debuglevel.bragi.chapter.Chapter
import de.debuglevel.bragi.character.Character
import de.debuglevel.bragi.places.Place

interface DocumentRenderer {
    // TODO: should rather accept a Book as parameter when "Book" exists
    fun render(chapters: List<Chapter>, characters: List<Character>, places: List<Place>): Any
}