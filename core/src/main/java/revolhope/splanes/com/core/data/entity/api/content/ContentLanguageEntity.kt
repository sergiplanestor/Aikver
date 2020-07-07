package revolhope.splanes.com.core.data.entity.api.content

import com.google.gson.annotations.SerializedName

class ContentLanguageEntity(
    @SerializedName("iso_639_1") val iso: String?,
    @SerializedName("name") val name: String?
)