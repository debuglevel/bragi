package de.debuglevel.bragi.render.odt

import de.debuglevel.bragi.chapter.Chapter
import de.debuglevel.bragi.character.Character
import de.debuglevel.bragi.places.Place
import de.debuglevel.bragi.render.DocumentRenderer
import mu.KotlinLogging
import org.odftoolkit.odfdom.doc.OdfTextDocument
import java.io.File
import javax.inject.Singleton

@Singleton
class OdtRenderer : DocumentRenderer {
    private val logger = KotlinLogging.logger {}

    override fun render(chapters: List<Chapter>, characters: List<Character>, places: List<Place>): Any {
        val document = createDocument()
        //renderHead(document)
        renderFrontMatter(document)
        renderChapters(document, chapters)
        //renderCharacters(document, characters)
        //renderPlaces(document, places)
        //renderBackMatter(document)
        //renderFoot(document)

        val file = saveDocument(document)

        return file
    }

    private fun renderChapters(document: OdfTextDocument, chapters: List<Chapter>) {
        logger.debug { "Rendering ${chapters.size} chapters..." }

        var output = "<h1>Chapters</h1>"
        output += chapters.forEach {
            val title = document.newParagraph()
            title.addContent(it.title)

            val content = document.newParagraph()
            content.addContent(it.content)

            val summaryHeading = document.newParagraph()
            summaryHeading.addContent("Summary")

            val summary = document.newParagraph()
            summary.addContent(it.summary)

            val notesHeading = document.newParagraph()
            notesHeading.addContent("Notes")

            val notes = document.newParagraph()
            notes.addContent(it.notes)
        }
    }

    private fun renderFrontMatter(document: OdfTextDocument) {
    }

    private fun saveDocument(document: OdfTextDocument): File {
        val tempFile = createTempFile("bragi-odt-export", suffix = ".odt")
        document.save(tempFile)
        return tempFile
    }

    private fun createDocument(): OdfTextDocument {
        // Create a text document from a standard template (empty documents within the JAR)
        val odt = OdfTextDocument.newTextDocument()

        return odt
    }
}