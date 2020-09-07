package revolhope.splanes.com.core.data.entity.api.content

import com.google.gson.annotations.SerializedName

data class ContentProductionCompanyEntity(
    @SerializedName("name") val name: String?,
    @SerializedName("id") val id: Int?,
    @SerializedName("logo_path") val logo: String?,
    @SerializedName("origin_country") val originCounty: String?
)