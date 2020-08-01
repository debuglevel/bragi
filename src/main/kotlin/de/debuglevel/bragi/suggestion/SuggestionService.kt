package de.debuglevel.bragi.suggestion

interface SuggestionService<T> {
    fun getSuggested(text: String): Set<T>
}