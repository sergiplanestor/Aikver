package revolhope.splanes.com.core.data.entity.user

data class UserEntity(
    val id: String?,
    val username: String?,
    val avatar: UserAvatarEntity?,
    var selectedUserGroup: String?,
    val userGroups: MutableList<String> = mutableListOf(),
    val numOfContentAddedByUser: Int?,
    val createdOn: Long?
)