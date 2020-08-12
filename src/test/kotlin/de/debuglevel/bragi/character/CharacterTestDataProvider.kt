package de.debuglevel.bragi.character

import mu.KotlinLogging
import java.util.stream.Stream

object CharacterTestDataProvider {
    private val logger = KotlinLogging.logger {}

    data class FirstnameTestData(
        val name: String,
        val firstName: String
    )

    fun firstnameItemProvider() = Stream.of(
        FirstnameTestData(name = "Arya", firstName = "Arya"),
        FirstnameTestData(name = " Arya", firstName = "Arya"),
        FirstnameTestData(name = "Arya ", firstName = "Arya"),
        FirstnameTestData(name = "Arya Stark", firstName = "Arya"),
        FirstnameTestData(name = "Arya  Stark", firstName = "Arya"),
        FirstnameTestData(name = " Arya Stark", firstName = "Arya"),
        FirstnameTestData(name = "Arya Stark ", firstName = "Arya"),
        FirstnameTestData(name = " Arya Stark ", firstName = "Arya"),
        FirstnameTestData(name = "Albus Percival Wulfric Brian Dumbledore", firstName = "Albus"),
        FirstnameTestData(name = "Karl-Heinz Schmidt", firstName = "Karl-Heinz")
    )

    fun itemProvider() = Stream.of(
        // very basic
        Character(
            id = null,
            name = "Basic Character",
            aliases = mutableListOf(),
            notes = "",
            picture = null
        ),
        // aliases
        Character(
            id = null,
            name = "Alias Character",
            aliases = mutableListOf("alias1"),
            notes = "",
            picture = null
        ),
        Character(
            id = null,
            name = "Aliases Character",
            aliases = mutableListOf("alias 1", "alias 2"),
            notes = "",
            picture = null
        ),
        // notes
        Character(
            id = null,
            name = "Note Character",
            aliases = mutableListOf(),
            notes = "A note",
            picture = null
        ),
        Character(
            id = null,
            name = "Long Notes Character",
            aliases = mutableListOf(),
            notes = "LongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotesLongNotes",
            picture = null
        ),
        // picture
        Character(
            id = null,
            name = "Picture Character",
            aliases = mutableListOf(),
            notes = "",
            picture = "/9j/4AAQSkZJRgABAQEBLAEsAAD//gATQ3JlYXRlZCB3aXRoIEdJTVD/2wBDAAMCAgMCAgMDAwMEAwMEBQgFBQQEBQoHBwYIDAoMDAsKCwsNDhIQDQ4RDgsLEBYQERMUFRUVDA8XGBYUGBIUFRT/2wBDAQMEBAUEBQkFBQkUDQsNFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBT/wgARCAABAAEDAREAAhEBAxEB/8QAFAABAAAAAAAAAAAAAAAAAAAACP/EABUBAQEAAAAAAAAAAAAAAAAAAAMF/9oADAMBAAIQAxAAAAFQTW//xAAUEAEAAAAAAAAAAAAAAAAAAAAA/9oACAEBAAEFAn//xAAUEQEAAAAAAAAAAAAAAAAAAAAA/9oACAEDAQE/AX//xAAUEQEAAAAAAAAAAAAAAAAAAAAA/9oACAECAQE/AX//xAAUEAEAAAAAAAAAAAAAAAAAAAAA/9oACAEBAAY/An//xAAUEAEAAAAAAAAAAAAAAAAAAAAA/9oACAEBAAE/IX//2gAMAwEAAgADAAAAEH//xAAUEQEAAAAAAAAAAAAAAAAAAAAA/9oACAEDAQE/EH//xAAUEQEAAAAAAAAAAAAAAAAAAAAA/9oACAECAQE/EH//xAAUEAEAAAAAAAAAAAAAAAAAAAAA/9oACAEBAAE/EH//2Q=="
        ),
        // umlauts etc
        Character(
            id = null,
            name = "Umlauts Character",
            aliases = mutableListOf("ööö", "äää", "ßßß"),
            notes = "ÖÜÄß",
            picture = null
        ),
        Character(
            id = null,
            name = "Unicode Character コハウプト マルク",
            aliases = mutableListOf("コハウプト", "マルク"),
            notes = "マルク",
            picture = null
        )
    )

    data class SuggestionTestData(
        val existingCharacters: List<Character>,
        val occurringCharacters: List<Character>,
        val text: String
    )

    fun suggestionItemProvider(): List<SuggestionTestData> {
        val arya = Character(
            id = null,
            name = "Arya Stark",
            aliases = mutableListOf("Arya"),
            notes = "",
            picture = null
        )
        val eddard = Character(
            id = null,
            name = "Eddard Stark",
            aliases = mutableListOf("Eddard", "Ned"),
            notes = "",
            picture = null
        )

        //return listOf<SuggestionTestData>(
        val arya1 = arya.copy()
        val eddard1 = eddard.copy()
        val test1 =
            SuggestionTestData(
                existingCharacters = listOf(arya1, eddard1),
                text = "Arya and Eddard like wolves.",
                occurringCharacters = listOf(arya1, eddard1)
            )
        val arya2 = arya.copy()
        val eddard2 = eddard.copy()
        val test2 =
            SuggestionTestData(
                existingCharacters = listOf(arya2, eddard2),
                text = "Arya and Ned like wolves.",
                occurringCharacters = listOf(arya2, eddard2)
            )
        val arya3 = arya.copy()
        val eddard3 = eddard.copy()
        val test3 =
            SuggestionTestData(
                existingCharacters = listOf(arya3, eddard3),
                text = "Arya Stark and Eddard Stark like wolves.",
                occurringCharacters = listOf(arya3, eddard3)
            )
        val arya4 = arya.copy()
        val eddard4 = eddard.copy()
        val test4 =
            SuggestionTestData(
                existingCharacters = listOf(arya4, eddard4),
                text = "Mrs. Stark and Mr. Stark like wolves.",
                occurringCharacters = listOf()
            )
        val arya5 = arya.copy()
        val eddard5 = eddard.copy()
        val test5 =
            SuggestionTestData(
                existingCharacters = listOf(arya5, eddard5),
                text = "Arya and har father like wolves.",
                occurringCharacters = listOf(arya5)
            )
        val arya6 = arya.copy()
        val eddard6 = eddard.copy()
        val test6 =
            SuggestionTestData(
                existingCharacters = listOf(arya6, eddard6),
                text = "Ned and his daughter like wolves.",
                occurringCharacters = listOf(eddard6)
            )
        val arya7 = arya.copy()
        val eddard7 = eddard.copy()
        val test7 =
            SuggestionTestData(
                existingCharacters = listOf(arya7, eddard7),
                text = "Ned Stark and his daughter like wolves.",
                occurringCharacters = listOf(eddard7)
            )
        val arya8 = arya.copy()
        val eddard8 = eddard.copy()
        val test8 =
            SuggestionTestData(
                existingCharacters = listOf(arya8, eddard8),
                text = "Jon Snow like wolves.",
                occurringCharacters = listOf()
            )
        val arya9 = arya.copy()
        val eddard9 = eddard.copy()
        val test9 =
            SuggestionTestData(
                existingCharacters = listOf(arya9, eddard9),
                text = "Arya, Jon and Sansa like wolves.",
                occurringCharacters = listOf(arya9)
            )
        val arya10 = arya.copy()
        val eddard10 = eddard.copy()
        val test10 =
            SuggestionTestData(
                existingCharacters = listOf(arya10, eddard10),
                text = "Arya! She likes wolves.",
                occurringCharacters = listOf(arya10)
            )
        val arya11 = arya.copy()
        val eddard11 = eddard.copy()
        val test11 =
            SuggestionTestData(
                existingCharacters = listOf(arya11, eddard11),
                text = "That little child (Arya) likes wolves.",
                occurringCharacters = listOf(arya11)
            )
        //val arya12 = arya.copy()
        //val eddard12 = eddard.copy()
//        val test12 =
//            SuggestionTestData(
//                existingCharacters = listOf(arya12, eddard12),
//                text = "omgAryaSure?",
//                occurringCharacters = listOf(arya12)),
        val arya13 = arya.copy()
        val eddard13 = eddard.copy()
        val test13 =
            SuggestionTestData(
                existingCharacters = listOf(arya13, eddard13),
                text = "He asked 'lol, arya sure?'",
                occurringCharacters = listOf()
            )
        val arya14 = arya.copy()
        val eddard14 = eddard.copy()
        val test14 =
            SuggestionTestData(
                existingCharacters = listOf(arya14, eddard14),
                text = "He shouted: 'Arya, there you are!'",
                occurringCharacters = listOf(arya14)
            )
        //)

        return listOf(
            test1,
            test2,
            test3,
            test4,
            test5,
            test6,
            test7,
            test8,
            test9,
            test10,
            test11,
            //test12,
            test13,
            test14
        )
    }
}