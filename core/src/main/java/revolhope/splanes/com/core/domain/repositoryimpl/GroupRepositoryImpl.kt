package revolhope.splanes.com.core.domain.repositoryimpl

import revolhope.splanes.com.core.data.datasource.FirebaseDataSource
import revolhope.splanes.com.core.data.repository.GroupRepository
import revolhope.splanes.com.core.domain.mapper.UserGroupMapper
import revolhope.splanes.com.core.domain.mapper.UserMapper
import revolhope.splanes.com.core.domain.model.User
import revolhope.splanes.com.core.domain.model.UserGroup

class GroupRepositoryImpl(
    private val firebaseDataSource: FirebaseDataSource
) : GroupRepository {

    /*override suspend fun fetchUserGroup(user: User, userGroupId: String): UserGroup? =
        firebaseDataSource.fetchUserGroup(
            UserMapper.fromUserModelToEntity(user),
            userGroupId
        )

    override suspend fun fetchUserGroups(user: User, limitTo: Int): List<UserGroup> =
        firebaseDataSource.fetchUserGroups(user, limitTo) ?: mutableListOf()*/

    override suspend fun insertUserGroup(userGroup: UserGroup): Boolean =
        firebaseDataSource.insertUserGroup(userGroup.let(UserGroupMapper::fromModelToEntity))

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