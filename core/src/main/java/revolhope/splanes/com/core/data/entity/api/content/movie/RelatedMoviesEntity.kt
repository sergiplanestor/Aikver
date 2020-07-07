package revolhope.splanes.com.core.data.entity.api.content.movie

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RelatedMoviesEntity(
    @SerializedName("page") val page: Int?,
    @SerializedName("results") val results: List<MovieEntity>?,
    @SerializedName("total_pages") val totalPages: Int?,
    @SerializedName("total_results") val totalResults: Int?
) : Serializable