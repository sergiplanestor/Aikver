package revolhope.splanes.com.core.data.entity.content

data class CustomSerieEntity(
    val serieId: String?,
    val userAdded: String?,
    val dateAdded: Long?,
    val seenBy: List<String>?,
    val network: Int?,
    val punctuation: List<Pair<String, Float>>?,
    val comments: List<Pair<String, String>>?
)