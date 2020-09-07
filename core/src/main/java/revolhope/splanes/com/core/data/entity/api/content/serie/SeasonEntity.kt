package revolhope.splanes.com.core.data.entity.api.content.serie

import com.google.gson.annotations.SerializedName

data class SeasonEntity(
    @SerializedName("air_date") val airDate: String?,
    @SerializedName("episode_count") val episodeCount: Int?,
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("poster_path") val thumbnail: String?,
    @SerializedName("season_number") val numSeason: Int?
)