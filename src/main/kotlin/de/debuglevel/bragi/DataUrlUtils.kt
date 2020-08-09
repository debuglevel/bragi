package de.debuglevel.bragi

object DataUrlUtils {
    /**
     * Gets the Base64 part of a data URL.
     */
    fun getBase64Part(dataUrl: String?): String? {
        val encodingPrefix = "base64,"

        return when {
            dataUrl.isNullOrBlank() -> null
            !dataUrl.contains(encodingPrefix) -> null
            else -> {
                val contentStartIndex = dataUrl.indexOf(encodingPrefix) + encodingPrefix.length
                val base64 = dataUrl.substring(contentStartIndex)
                base64
            }
        }
    }
}