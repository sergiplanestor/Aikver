package revolhope.splanes.com.core.data.repository

import revolhope.splanes.com.core.domain.model.User
import revolhope.splanes.com.core.domain.model.UserGroup
import revolhope.splanes.com.core.domain.model.UserGroupMember

interface GroupRepository {

    suspend fun insertUserGroup(groupName: String) : Boolean

    suspend fun insertMember(username: String, userGroup: UserGroup) : Boolean

    suspend fun updateUserGroup(userGroup: UserGroup) : Boolean

    suspend fun deleteMember(member: UserGroupMember) : Boolean

    suspend fun deleteUserGroup(userGroup: UserGroup) : Boolean
}