package revolhope.splanes.com.core.data.entity.api.content.serie

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class QuerySeriesEntity(
    @SerializedName("page") val page: Int?,
    @SerializedName("results") val results: List<SerieEntity>?,
    @SerializedName("total_pages") val totalPages: Int?,
    @SerializedName("total_results") val totalResults: Int?
) : Serializable