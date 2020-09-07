package revolhope.splanes.com.core.data.entity.api.content

import com.google.gson.annotations.SerializedName

data class ContentGenresEntity(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?
)