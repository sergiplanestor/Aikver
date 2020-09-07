package revolhope.splanes.com.core.data.entity.api.content.movie

import com.google.gson.annotations.SerializedName
import revolhope.splanes.com.core.data.entity.api.content.ContentCollectionEntity
import revolhope.splanes.com.core.data.entity.api.content.ContentGenresEntity
import revolhope.splanes.com.core.data.entity.api.content.ContentLanguageEntity
import revolhope.splanes.com.core.data.entity.api.content.ContentProductionCompanyEntity
import revolhope.splanes.com.core.data.entity.api.content.ContentProductionCountryEntity

data class MovieDetailsEntity(
    @SerializedName("backdrop_path") val backdrop: String?,
    @SerializedName("adult") val isAdult: Boolean?,
    @SerializedName("belongs_to_collection") val collection: ContentCollectionEntity?,
    @SerializedName("budget") val budget: Int?,
    @SerializedName("genres") val genres: List<ContentGenresEntity>?,
    @SerializedName("homepage") val homepage: String?,
    @SerializedName("id") val id: Int?,
    @SerializedName("imdb_id") val idIMBD: String?,
    @SerializedName("original_language") val originalLanguage: String?,
    @SerializedName("original_title") val originalTitle: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("popularity") val popularity: Float?,
    @SerializedName("poster_path") val thumbnail: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("revenue") val revenue: Int?,
    @SerializedName("runtime") val runtime: Int?,
    @SerializedName("spoken_languages") val languages: List<ContentLanguageEntity>?,
    @SerializedName("status") val status: String?,
    @SerializedName("tagline") val tagLine: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("video") val haveVideo: Boolean?,
    @SerializedName("production_companies") val productionCompanies: List<ContentProductionCompanyEntity>?,
    @SerializedName("production_countries") val productionCountries: List<ContentProductionCountryEntity>?,
    @SerializedName("vote_average") val voteAverage: Float?,
    @SerializedName("vote_count") val voteCount: Int?
)