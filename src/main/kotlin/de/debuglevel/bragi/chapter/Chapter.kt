package de.debuglevel.bragi.chapter

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
    var content: String
)