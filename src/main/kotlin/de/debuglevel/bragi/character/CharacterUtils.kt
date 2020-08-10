package de.debuglevel.bragi.character

object CharacterUtils {
    /**
     * Gets the first name of a full name.
     * Does not include middle names.
     */
    fun extractFirstName(name: String): String {
        if (name.isBlank()) {
            throw IllegalArgumentException("name must not be blank")
        }
        val trimmedName = name.trim()

        return trimmedName.split(" ").first()
    }
}