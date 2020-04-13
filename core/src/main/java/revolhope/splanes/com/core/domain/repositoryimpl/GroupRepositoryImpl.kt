package revolhope.splanes.com.core.domain.repositoryimpl

import revolhope.splanes.com.core.data.datasource.FirebaseDataSource
import revolhope.splanes.com.core.data.repository.GroupRepository
import revolhope.splanes.com.core.data.repository.UserRepository
import revolhope.splanes.com.core.domain.mapper.UserGroupMapper
import revolhope.splanes.com.core.domain.model.User
import revolhope.splanes.com.core.domain.model.UserGroup

class GroupRepositoryImpl(
    private val userRepository: UserRepository,
    private val firebaseDataSource: FirebaseDataSource
) : GroupRepository {

    override suspend fun insertUserGroup(userGroup: UserGroup): Boolean =
        firebaseDataSource.insertUserGroup(userGroup.let(UserGroupMapper::fromModelToEntity))

    override suspend fun insertMember(user: String, userGroup: UserGroup): Boolean =
        (firebaseDataSource.fetchUserGroup(
            userGroup.userGroupAdmin.userId,
            userGroup.id
        )?.let { groupEntity ->
            firebaseDataSource.fetchUserByName(user)?.let { user ->
                groupEntity.members?.add(user.id ?: "")
                firebaseDataSource.insertUserGroup(groupEntity)
                firebaseDataSource.fetchUser(user.id ?: "")?.let { userEntity ->
                    if (userEntity.selectedUserGroup == null && userEntity.userGroups?.size == 0) {
                        userEntity.selectedUserGroup = UserGroupMapper.fromModelToEntity(userGroup)
                    }
                    userEntity.userGroups?.add(UserGroupMapper.fromModelToEntity(userGroup))
                    firebaseDataSource.insertUser(userEntity)
                }
            }
        } ?: false).also {
            if (it) userRepository.fetchUser(forceCall = true)
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