package de.debuglevel.bragi.character

import io.micronaut.data.annotation.DateCreated
import io.micronaut.data.annotation.DateUpdated
import java.time.LocalDateTime
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Character(
    @Id
    @GeneratedValue
    var id: UUID?,
    var name: String,
    var notes: String,
    @DateCreated
    var createdOn: LocalDateTime = LocalDateTime.now(),
    @DateUpdated
    var lastModified: LocalDateTime = LocalDateTime.now()
)