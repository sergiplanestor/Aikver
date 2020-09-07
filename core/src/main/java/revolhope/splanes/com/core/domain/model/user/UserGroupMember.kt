package revolhope.splanes.com.core.domain.model.user

import java.io.Serializable

data class UserGroupMember(
    val userId: String,
    val groupId: String,
    val avatar: UserAvatar,
    val username: String,
    val isUserGroupAdmin: Boolean
) : Serializable {
    companion object {
        val empty: UserGroupMember
            get() = UserGroupMember(
                userId = "",
                groupId = "",
                avatar = UserAvatar(),
                username = "",
                isUserGroupAdmin = false
            )
    }
}