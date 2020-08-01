package de.debuglevel.bragi.chapter

import java.util.stream.Stream

object ChapterTestDataProvider {
    fun itemProvider() = Stream.of(
        Chapter(
            id = null,
            title = "Chapter 1",
            content = "Content",
            summary = "Summary",
            notes = "Notes"
        ),
        Chapter(
            id = null,
            title = "Short Title",
            content = "LongContentLongContentLongContentLongContentLongContentLongContentLongContentLongContentLongContentLongContentLongContentLongContentLongContentLongContentLongContentLongContentLongContentLongContentLongContentLongContentLongContentLongContentLongContentLongContentLongContentLongContent",
            summary = "LongSummaryLongSummaryLongSummaryLongSummaryLongSummaryLongSummaryLongSummaryLongSummaryLongSummaryLongSummaryLongSummaryLongSummaryLongSummaryLongSummaryLongSummaryLongSummaryLongSummaryLongSummaryLongSummaryLongSummaryLongSummaryLongSummaryLongSummaryLongSummaryLongSummaryLongSummary",
            notes = "LongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotes"
        ),
        Chapter(
            id = null,
            title = "Chapter ÖÜÄß",
            content = "",
            summary = "",
            notes = ""
        ),
        Chapter(
            id = null,
            title = "コハウプト マルク",
            content = "",
            summary = "",
            notes = ""
        )
    )
}