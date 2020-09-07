package revolhope.splanes.com.core.data.entity.api.configuration

import com.google.gson.annotations.SerializedName

data class ConfigurationEntity(
    @SerializedName("images") val imageConfigEntity: ImageConfigurationEntity?,
    @SerializedName("change_keys") val changeKeysValue: List<String>?
)