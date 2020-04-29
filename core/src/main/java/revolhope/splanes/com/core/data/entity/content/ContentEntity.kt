package revolhope.splanes.com.core.data.entity.content

abstract class ContentEntity {
    abstract val thumbnail: String?
    abstract val popularity: Float?
    abstract val id: Int?
    abstract val backdrop: Int?
    abstract val voteAverage: Float?
    abstract val overview: String?
    abstract val genreIds: List<Int>?
    abstract val originalLanguage: String?
    abstract val voteCount: Int?
}