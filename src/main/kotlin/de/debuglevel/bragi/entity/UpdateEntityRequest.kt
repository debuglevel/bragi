package de.debuglevel.bragi.entity

interface UpdateEntityRequest<T> {
    fun toEntity(): T
}