package de.debuglevel.bragi.entity

import io.micronaut.data.repository.CrudRepository
import java.util.*

interface EntityRepository<T> : CrudRepository<T, UUID>