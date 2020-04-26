package revolhope.splanes.com.core.data.entity

data class UserEntity(
    val id: String?,
    val username: String?,
    val avatar: UserAvatarEntity?,
    var selectedUserGroup: String?,
    val userGroups: MutableList<String> = mutableListOf()
)