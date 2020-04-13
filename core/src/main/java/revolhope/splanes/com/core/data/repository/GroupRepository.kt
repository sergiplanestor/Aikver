package revolhope.splanes.com.core.data.repository

import revolhope.splanes.com.core.domain.model.User
import revolhope.splanes.com.core.domain.model.UserGroup

interface GroupRepository {

    suspend fun insertUserGroup(userGroup: UserGroup) : Boolean

    suspend fun insertMember(user: String, userGroup: UserGroup) : Boolean

    suspend fun updateUserGroup(userGroup: UserGroup) : Boolean

    suspend fun deleteMember(user: User, userGroup: UserGroup) : Boolean

    suspend fun deleteUserGroup(userGroup: UserGroup) : Boolean
}