package de.debuglevel.bragi.chapter

import de.debuglevel.bragi.character.Character
import io.micronaut.data.annotation.DateCreated
import io.micronaut.data.annotation.DateUpdated
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

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
    @Lob
    var notes: String,
    @ManyToMany()
    var characters: List<Character>,
    @DateCreated
    override var createdOn: LocalDateTime = LocalDateTime.now(),
    @DateUpdated
    override var lastModified: LocalDateTime = LocalDateTime.now()
) : de.debuglevel.bragi.entity.Entity