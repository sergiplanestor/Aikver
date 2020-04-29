package revolhope.splanes.com.core.data.entity.content

import com.google.gson.annotations.SerializedName

data class SearchContentEntity<T : ContentEntity>(
    @SerializedName("page") val page: Int?,
    @SerializedName("results") val results: List<T>,
    @SerializedName("total_results") val totalResults: Int?,
    @SerializedName("total_pages") val totalPages: Int?
)