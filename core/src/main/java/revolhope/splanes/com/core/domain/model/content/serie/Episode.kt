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
) {
    companion object {
        fun getEmpty(): Episode =
            Episode(
                airDate = "",
                numEpisode = -1,
                id = -1,
                name = "",
                overview = "",
                productionCode = "",
                numSeason = -1,
                showId = -1,
                still = "",
                voteCount = -1,
                voteAverage = Float.NaN
            )
    }
}