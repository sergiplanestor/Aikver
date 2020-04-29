package revolhope.splanes.com.core.data.entity.content.movie

import com.google.gson.annotations.SerializedName
import revolhope.splanes.com.core.data.entity.content.ContentEntity

data class MovieEntity(
    @SerializedName("poster_path") override val thumbnail: String?,
    @SerializedName("popularity") override val popularity: Float?,
    @SerializedName("id") override val id: Int?,
    @SerializedName("backdrop_path") override val backdrop: String?,
    @SerializedName("vote_average") override val voteAverage: Float?,
    @SerializedName("overview") override val overview: String?,
    @SerializedName("genre_ids") override val genreIds: List<Int>?,
    @SerializedName("original_language") override val originalLanguage: String?,
    @SerializedName("vote_count") override val voteCount: Int?,
    @SerializedName("adult") val adult: Boolean?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("original_title") val originalTitle: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("video") val hasVideo: Boolean?
) : ContentEntity()