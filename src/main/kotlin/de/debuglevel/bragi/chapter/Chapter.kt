package de.debuglevel.bragi.chapter

import io.micronaut.data.annotation.DateCreated
import io.micronaut.data.annotation.DateUpdated
import java.time.LocalDateTime
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Lob

@Entity
data class Chapter(
    @Id
    @GeneratedValue
    override var id: UUID?,
    var title: String,
    @Lob
    var content: String,
    @Lob
    var summary: String,
    @DateCreated
    override var createdOn: LocalDateTime = LocalDateTime.now(),
    @DateUpdated
    override var lastModified: LocalDateTime = LocalDateTime.now()
) : de.debuglevel.bragi.entity.Entity