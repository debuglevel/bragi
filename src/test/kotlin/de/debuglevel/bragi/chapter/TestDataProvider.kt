package de.debuglevel.bragi.chapter

import java.util.stream.Stream

object TestDataProvider {
    fun chapterProvider() = Stream.of(
        Chapter(
            id = null,
            title = "Mozart"
        ),
        Chapter(
            id = null,
            title = "Hänschen"
        ),
        Chapter(
            id = null,
            title = "コハウプト マルク"
        ),
        Chapter(
            id = null,
            title = "Max Mustermann"
        )
    )
}