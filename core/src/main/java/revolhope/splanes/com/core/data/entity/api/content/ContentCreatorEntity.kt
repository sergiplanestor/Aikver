package revolhope.splanes.com.core.data.entity.api.content

import com.google.gson.annotations.SerializedName

data class ContentCreatorEntity(
    @SerializedName("id") val id: Int?,
    @SerializedName("credit_id") val creditId: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("gender") val gender: Int?,
    @SerializedName("profile_path") val profile: String?
)