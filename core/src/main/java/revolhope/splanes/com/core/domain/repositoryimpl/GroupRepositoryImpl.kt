package revolhope.splanes.com.core.domain.repositoryimpl

import revolhope.splanes.com.core.data.datasource.FirebaseDataSource
import revolhope.splanes.com.core.data.repository.GroupRepository
import revolhope.splanes.com.core.domain.model.User
import revolhope.splanes.com.core.domain.model.UserGroup

class GroupRepositoryImpl(
    private val firebaseDataSource: FirebaseDataSource
) : GroupRepository {

    override suspend fun fetchUserGroups(user: User): List<UserGroup> {
        TODO("Not yet implemented")
    }

    override suspend fun insertUserGroup(userGroup: UserGroup): Boolean =
        firebaseDataSource.insertUserGroup(userGroup)

    override suspend fun insertMember(user: User, userGroup: UserGroup): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserGroup(userGroup: UserGroup): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMember(user: User, userGroup: UserGroup): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUserGroup(userGroup: UserGroup): Boolean {
        TODO("Not yet implemented")
    }
}