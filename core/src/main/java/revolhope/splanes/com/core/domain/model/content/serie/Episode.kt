package revolhope.splanes.com.core.domain.model.content.serie

data class Episode(
    val airDate: String,
    val numEpisode: Int,
    val id: Int,
    val name: String,
    val overview: String,
    val productionCode: String,
    val numSeason: Int,
    val showId: Int,
    val still: String,
    val voteAverage: Float,
    val voteCount: Int
)