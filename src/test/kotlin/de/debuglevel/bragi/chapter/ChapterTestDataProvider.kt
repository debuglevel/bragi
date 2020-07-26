package de.debuglevel.bragi.chapter

import java.util.stream.Stream

object ChapterTestDataProvider {
    fun itemProvider() = Stream.of(
        Chapter(
            id = null,
            title = "Chapter 1",
            content = ""
        ),
        Chapter(
            id = null,
            title = "Chapter ÖÜÄß",
            content = ""
        ),
        Chapter(
            id = null,
            title = "コハウプト マルク",
            content = ""
        )
    )
}