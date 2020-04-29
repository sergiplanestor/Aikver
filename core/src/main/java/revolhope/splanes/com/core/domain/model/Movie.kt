package revolhope.splanes.com.core.domain.model

data class Movie(
    val thumbnail: String,
    val popularity: Float,
    val id: Int,
    val backdrop: String,
    val voteAverage: Float,
    val overview: String,
    val genreIds: List<Int>,
    val originalLanguage: String,
    val voteCount: Int,
    val adult: Boolean,
    val releaseDate: String,
    val originalTitle: String,
    val title: String,
    val hasVideo: Boolean
)