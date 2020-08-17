package de.debuglevel.bragi

object DataUrlUtils {
    /**
     * Gets the Base64 part of a data URL.
     */
    fun getBase64Part(dataUrl: String?): String? {
        val encodingPrefix = "base64,"

        return when {
            dataUrl.isNullOrBlank() -> throw IllegalArgumentException("dataUrl must not be null or blank")
            !isDataUrl(dataUrl) -> throw IllegalArgumentException("dataUrl must begin with 'data:'")
            !dataUrl.contains(encodingPrefix) -> throw IllegalArgumentException("dataUrl must be base64 encoded")
            else -> {
                val contentStartIndex = dataUrl.indexOf(encodingPrefix) + encodingPrefix.length
                val base64 = dataUrl.substring(contentStartIndex)
                base64
            }
        }
    }

    fun isDataUrl(dataUrl: String): Boolean {
        return dataUrl.contains("data:")
    }
}