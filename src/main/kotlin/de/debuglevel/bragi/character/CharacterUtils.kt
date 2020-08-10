package de.debuglevel.bragi.character

object CharacterUtils {
    fun extractFirstName(name: String): String {
        if (name.isBlank()) {
            throw IllegalArgumentException("name must not be blank")
        }
        val trimmedName = name.trim()

        return trimmedName.split(" ").first()
    }
}