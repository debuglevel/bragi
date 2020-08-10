package de.debuglevel.bragi.character

object CharacterUtils {
    fun extractFirstName(name: String): String {
        return name.split(" ").first()
    }
}