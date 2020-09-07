package revolhope.splanes.com.core.data.entity.api.content

import com.google.gson.annotations.SerializedName

data class ContentCollectionEntity(
    @SerializedName("name") val name: String?,
    @SerializedName("id") val id: Int?,
    @SerializedName("poster_path") val logo: String?,
    @SerializedName("backdrop_path") val backdrop: String?
)