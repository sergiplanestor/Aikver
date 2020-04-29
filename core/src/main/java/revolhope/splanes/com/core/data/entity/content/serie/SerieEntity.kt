package revolhope.splanes.com.core.data.entity.content.serie

import com.google.gson.annotations.SerializedName
import revolhope.splanes.com.core.data.entity.content.ContentEntity

data class SerieEntity(
    @SerializedName("poster_path") override val thumbnail: String?,
    @SerializedName("popularity") override val popularity: Float?,
    @SerializedName("id") override val id: Int?,
    @SerializedName("backdrop_path") override val backdrop: Int?,
    @SerializedName("vote_average") override val voteAverage: Float?,
    @SerializedName("overview") override val overview: String?,
    @SerializedName("genre_ids") override val genreIds: List<Int>?,
    @SerializedName("original_language") override val originalLanguage: String?,
    @SerializedName("vote_count") override val voteCount: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("original_name") val originalName: String?,
    @SerializedName("first_air_date") val firstAirDate: String?,
    @SerializedName("origin_country") val originalCountries: List<String>?
) : ContentEntity()