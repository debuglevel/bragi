package de.debuglevel.bragi.picture

import java.util.*

interface PictureProvider {
    /**
     * Gets a picture as Base64
     */
    fun getPicture(id: UUID): ByteArray

    class PictureNotFoundException(criteria: Any) : Exception("Picture '$criteria' does not exist.")
}