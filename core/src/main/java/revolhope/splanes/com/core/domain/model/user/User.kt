package revolhope.splanes.com.core.domain.model.user

import revolhope.splanes.com.core.domain.model.user.UserAvatar
import revolhope.splanes.com.core.domain.model.user.UserGroup

data class User(
    val id: String,
    var avatar: UserAvatar,
    val username: String,
    var selectedUserGroup: UserGroup?,
    val userGroups: MutableList<UserGroup>
)