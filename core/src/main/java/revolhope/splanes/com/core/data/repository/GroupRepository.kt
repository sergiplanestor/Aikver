package revolhope.splanes.com.core.data.repository

import revolhope.splanes.com.core.domain.model.User
import revolhope.splanes.com.core.domain.model.UserGroup

interface GroupRepository {

    // suspend fun fetchUserGroup(user:User, userGroupId: String) : UserGroup?

    // suspend fun fetchUserGroups(user: User, limitTo: Int = -1) : List<UserGroup>

    suspend fun insertUserGroup(userGroup: UserGroup) : Boolean

    suspend fun insertMember(user: User, userGroup: UserGroup) : Boolean

    suspend fun updateUserGroup(userGroup: UserGroup) : Boolean

    suspend fun deleteMember(user: User, userGroup: UserGroup) : Boolean

    suspend fun deleteUserGroup(userGroup: UserGroup) : Boolean
}