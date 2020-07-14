package revolhope.splanes.com.core.data.repository

import revolhope.splanes.com.core.domain.model.user.UserGroup
import revolhope.splanes.com.core.domain.model.user.UserGroupMember

interface GroupRepository {

    suspend fun insertUserGroup(groupName: String) : Boolean

    suspend fun insertMember(username: String, userGroup: UserGroup) : Boolean

    // TODO: delete this method if not necessary
    //suspend fun updateUserGroup(userGroup: UserGroup) : Boolean

    suspend fun deleteMember(member: UserGroupMember) : Boolean

    suspend fun deleteUserGroup(userGroup: UserGroup) : Boolean
}