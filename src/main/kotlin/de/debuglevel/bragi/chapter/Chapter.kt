package de.debuglevel.bragi.chapter

import io.micronaut.data.annotation.DateCreated
import io.micronaut.data.annotation.DateUpdated
import java.time.LocalDateTime
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Chapter(
    @Id
    @GeneratedValue
    var id: UUID?,
    var title: String,
    var content: String,
    @DateCreated
    var createdOn: LocalDateTime = LocalDateTime.now(),
    @DateUpdated
    var lastModified: LocalDateTime = LocalDateTime.now()
)