package revolhope.splanes.com.core.domain.model.user

import java.io.Serializable

data class UserAvatar(
    var eyes: String = "eyes1",
    var nose: String = "nose2",
    var mouth: String = "mouth5",
    var color: String = "FFF000"
) : Serializable