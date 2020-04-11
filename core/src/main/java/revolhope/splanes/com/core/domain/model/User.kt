package revolhope.splanes.com.core.domain.model

data class User(
    val id: String,
    var avatar: UserAvatar,
    val username: String,
    var selectedUserGroup: UserGroup?,
    val userGroups: MutableList<UserGroup>
)