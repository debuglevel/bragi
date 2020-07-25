package de.debuglevel.bragi.greeting

data class GreetingRequest(
    val name: String,
    val language: String?
)
