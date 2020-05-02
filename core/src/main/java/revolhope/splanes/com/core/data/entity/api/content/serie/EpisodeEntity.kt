package revolhope.splanes.com.core.data.entity.api.content.serie

import com.google.gson.annotations.SerializedName

data class EpisodeEntity(
    @SerializedName("air_date") val airDate: String?,
    @SerializedName("episode_number") val numEpisode: Int?,
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("production_code") val productionCode: String?,
    @SerializedName("season_number") val numSeason: Int?,
    @SerializedName("show_id") val showId: Int?,
    @SerializedName("still_path") val still: String?,
    @SerializedName("vote_average") val voteAverage: Float?,
    @SerializedName("vote_count") val voteCount: Int?
)