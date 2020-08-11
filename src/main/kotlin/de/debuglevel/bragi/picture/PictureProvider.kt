package de.debuglevel.bragi.picture

import java.util.*

interface PictureProvider {
    /**
     * Gets the picture if available. Resizes the picture if maxWidth or maxHeight is given while maintaining the aspect ratio.
     */
    fun getPicture(id: UUID, maxWidth: Int?, maxHeight: Int?): ByteArray

    class PictureNotFoundException(criteria: Any) : Exception("Picture '$criteria' does not exist.")
}