package de.debuglevel.bragi.render.odt

import de.debuglevel.bragi.chapter.Chapter
import de.debuglevel.bragi.character.Character
import de.debuglevel.bragi.places.Place
import de.debuglevel.bragi.render.DocumentRenderer
import javax.inject.Singleton

@Singleton
class OdtRenderer : DocumentRenderer {
    override fun render(chapters: List<Chapter>, characters: List<Character>, places: List<Place>): Any {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}