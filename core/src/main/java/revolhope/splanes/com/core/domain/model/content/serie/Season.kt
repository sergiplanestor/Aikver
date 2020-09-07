package revolhope.splanes.com.core.domain.model.content.serie

import java.io.Serializable

data class Season(
    val airDate: String,
    val episodeCount: Int,
    val id: Int,
    val name: String,
    val overview: String,
    val thumbnail: String,
    val numSeason: Int
) : Serializable