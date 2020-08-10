package de.debuglevel.bragi.suggestion

interface SuggestionProvider<T> {
    fun getSuggested(text: String): Set<T>
}