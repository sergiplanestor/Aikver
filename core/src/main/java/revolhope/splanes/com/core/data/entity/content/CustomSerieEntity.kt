package revolhope.splanes.com.core.data.entity.content

data class CustomSerieEntity(
    override val contentId: String?,
    override val userAdded: String?,
    override val dateAdded: Long?,
    override val seenBy: List<String>?,
    override val network: Int?,
    override val recommendedTo: List<String>?,
    override val punctuation: List<Pair<String, Float>>?,
    override val comments: List<Pair<String, String>>?
) : CustomContentEntity()