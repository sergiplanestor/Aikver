package revolhope.splanes.com.core.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserAvatarTypesFaceEntity(
    @SerializedName("eyes") val eyes: List<String>?,
    @SerializedName("nose") val nose: List<String>?,
    @SerializedName("mouth") val mouth: List<String>?
) : Serializable