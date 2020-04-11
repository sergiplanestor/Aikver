package revolhope.splanes.com.core.domain.model

data class UserGroup(
    val id: String,
    val icon: String,
    val name: String,
    val members: MutableList<UserGroupMember>,
    val userGroupAdmin: UserGroupMember,
    val dateCreation: Long
)