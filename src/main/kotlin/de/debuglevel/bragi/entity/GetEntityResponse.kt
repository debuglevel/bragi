package de.debuglevel.bragi.entity

import java.time.LocalDateTime
import java.util.*

interface GetEntityResponse {
    var id: UUID?
    var createdOn: LocalDateTime
    var lastModified: LocalDateTime
}