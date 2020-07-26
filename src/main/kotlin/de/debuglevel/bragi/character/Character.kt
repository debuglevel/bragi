package de.debuglevel.bragi.character

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
    var notes: String
)