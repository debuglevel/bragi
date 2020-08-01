package de.debuglevel.bragi.places

import io.micronaut.data.annotation.DateCreated
import io.micronaut.data.annotation.DateUpdated
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
data class Place(
    @Id
    @GeneratedValue
    override var id: UUID?,
    var name: String,
    @ElementCollection(fetch = FetchType.EAGER)
    var aliases: List<String>,
    @Lob
    var notes: String,
    @DateCreated
    override var createdOn: LocalDateTime = LocalDateTime.now(),
    @DateUpdated
    override var lastModified: LocalDateTime = LocalDateTime.now()
) : de.debuglevel.bragi.entity.Entity