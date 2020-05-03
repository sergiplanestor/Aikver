package revolhope.splanes.com.core.data.entity.api.content.serie

import com.google.gson.annotations.SerializedName
import revolhope.splanes.com.core.data.entity.api.content.ContentCreatorEntity
import revolhope.splanes.com.core.data.entity.api.content.ContentGenresEntity
import revolhope.splanes.com.core.data.entity.api.content.ContentNetworkEntity

data class SerieDetailsEntity(
    @SerializedName("backdrop_path") val backdrop: String?,
    @SerializedName("created_by") val createdBy: List<ContentCreatorEntity>?,
    @SerializedName("episode_run_time") val episodeRuntime: List<Int>?,
    @SerializedName("first_air_date") val firstAirDate: String?,
    @SerializedName("genres") val genres: List<ContentGenresEntity>?,
    @SerializedName("homepage") val homepage: String?,
    @SerializedName("id") val id: Int?,
    @SerializedName("in_production") val isInProduction: Boolean?,
    @SerializedName("languages") val languages: List<String>?,
    @SerializedName("last_air_date") val lastAirDate: String?,
    @SerializedName("last_episode_to_air") val lastEpisodeToAir: EpisodeEntity?,
    @SerializedName("name") val name: String?,
    @SerializedName("networks") val network: List<ContentNetworkEntity>?,
    @SerializedName("number_of_episodes") val numEpisodes: Int?,
    @SerializedName("number_of_seasons") val numSeasons: Int?,
    @SerializedName("origin_country") val originCountry: List<String>?,
    @SerializedName("original_language") val originalLanguage: String?,
    @SerializedName("original_name") val originalName: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("popularity") val popularity: Float?,
    @SerializedName("poster_path") val thumbnail: String?,
    @SerializedName("seasons") val seasons: List<SeasonEntity>?,
    @SerializedName("status") val status: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("vote_average") val voteAverage: Float?,
    @SerializedName("vote_count") val voteCount: Int?
)