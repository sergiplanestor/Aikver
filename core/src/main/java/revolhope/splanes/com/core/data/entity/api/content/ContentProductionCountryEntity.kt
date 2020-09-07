package revolhope.splanes.com.core.data.entity.api.content

import com.google.gson.annotations.SerializedName

data class ContentProductionCountryEntity(
    @SerializedName("iso_3166_1") val iso: String?,
    @SerializedName("name") val name: String?
)