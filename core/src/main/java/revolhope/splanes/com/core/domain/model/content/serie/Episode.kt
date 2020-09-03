package revolhope.splanes.com.core.domain.model.content.serie

import java.io.Serializable

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
) : Serializable {
    companion object {
        val empty: Episode get() =
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