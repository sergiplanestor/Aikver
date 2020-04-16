package revolhope.splanes.com.core.domain.repositoryimpl

import revolhope.splanes.com.core.data.datasource.FirebaseDataSource
import revolhope.splanes.com.core.data.repository.GroupRepository
import revolhope.splanes.com.core.data.repository.UserRepository
import revolhope.splanes.com.core.domain.mapper.UserGroupMapper
import revolhope.splanes.com.core.domain.mapper.UserMapper
import revolhope.splanes.com.core.domain.model.User
import revolhope.splanes.com.core.domain.model.UserGroup
import java.util.*

class GroupRepositoryImpl(
    private val userRepository: UserRepository,
    private val firebaseDataSource: FirebaseDataSource
) : GroupRepository {

    override suspend fun insertUserGroup(groupName: String): Boolean =
        (userRepository.fetchUser()?.let { user ->
            val member = UserMapper.fromUserModelToUserGroupMemberModel(
                model = user,
                groupId = UUID.randomUUID().toString().replace("-", ""),
                userGroupAdminId = user.id
            )
            val userGroup = UserGroup(
                id = member.groupId,
                icon = "", // TODO: Set default icon
                name = groupName,
                members = mutableListOf(member),
                userGroupAdmin = member,
                dateCreation = System.currentTimeMillis()
            )
            firebaseDataSource.insertUserGroup(
                userGroup.let(UserGroupMapper::fromModelToEntity)
            ).let {
                if (it) {
                    user.userGroups.add(userGroup)
                    firebaseDataSource.insertUser(user.let(UserMapper::fromUserModelToEntity))
                } else {
                    false
                }
            }
        } ?: false).also {
            if (it) userRepository.fetchUser(forceCall = true)
        }

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