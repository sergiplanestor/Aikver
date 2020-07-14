package revolhope.splanes.com.core.data.entity.content

abstract class CustomContentEntity {
    abstract val contentId: String?
    abstract val userAdded: String?
    abstract val dateAdded: Long?
    abstract val seenBy: List<String>?
    abstract val network: Int?
    abstract val recommendedTo: List<String>?
    abstract val punctuation: List<Pair<String, Float>>?
    abstract val comments: List<Pair<String, String>>?
}