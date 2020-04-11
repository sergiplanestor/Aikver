package revolhope.splanes.com.core.data.entity

data class UserEntity(
    val id: String?,
    val username: String?,
    val avatar: UserAvatarEntity?,
    val selectedUserGroup: String?,
    val userGroups: MutableList<String>? = mutableListOf()
)