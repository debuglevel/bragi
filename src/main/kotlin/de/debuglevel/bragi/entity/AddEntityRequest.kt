package de.debuglevel.bragi.entity

interface AddEntityRequest<T> {
    fun toEntity(): T
}