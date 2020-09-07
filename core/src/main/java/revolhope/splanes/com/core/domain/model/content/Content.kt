package revolhope.splanes.com.core.domain.model.content

import java.io.Serializable

abstract class Content : Serializable {
    abstract val thumbnail: String
    abstract val popularity: Float
    abstract val id: Int
    abstract val backdrop: String
    abstract val voteAverage: Float
    abstract val overview: String
    abstract val genreIds: List<Int>
    abstract val originalLanguage: String
    abstract val voteCount: Int
    abstract val title: String
    abstract val originalTitle: String
}