package revolhope.splanes.com.core.domain.model

data class Serie(
    val thumbnail: String,
    val popularity: Float,
    val id: Int,
    val backdrop: String,
    val voteAverage: Float,
    val overview: String,
    val genreIds: List<Int>,
    val originalLanguage: String,
    val voteCount: Int,
    val name: String,
    val originalName: String,
    val firstAirDate: String,
    val originalCountries: List<String>
)