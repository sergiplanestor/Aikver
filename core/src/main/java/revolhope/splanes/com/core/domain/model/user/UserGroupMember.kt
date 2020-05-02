package revolhope.splanes.com.core.domain.model.user

data class UserGroupMember(
    val userId: String,
    val groupId: String,
    val avatar: UserAvatar,
    val username: String,
    val isUserGroupAdmin: Boolean
)