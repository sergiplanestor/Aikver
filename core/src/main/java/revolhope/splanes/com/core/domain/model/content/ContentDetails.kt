package revolhope.splanes.com.core.domain.model.content


abstract class ContentDetails {
    abstract val id: Int
    abstract val title: String
    abstract val originalTitle: String
    abstract val originalLanguage: String
    abstract val overview: String
    abstract val genres: List<ContentGenres>
    abstract val homepage: String
    abstract val status: ContentStatus
    abstract val backdrop: String
    abstract val thumbnail: String
    abstract val popularity: Float
    abstract val voteAverage: Float
    abstract val voteCount: Int
}