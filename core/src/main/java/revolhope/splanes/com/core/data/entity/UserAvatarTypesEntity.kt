package revolhope.splanes.com.core.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserAvatarTypesEntity(
    @SerializedName("face") val face: UserAvatarTypesFaceEntity?
) : Serializable