package revolhope.splanes.com.core.domain.repositoryimpl

import revolhope.splanes.com.core.data.datasource.FirebaseDataSource
import revolhope.splanes.com.core.data.repository.GroupRepository
import revolhope.splanes.com.core.data.repository.UserRepository
import revolhope.splanes.com.core.domain.mapper.UserGroupMapper
import revolhope.splanes.com.core.domain.mapper.UserMapper
import revolhope.splanes.com.core.domain.model.UserGroup
import revolhope.splanes.com.core.domain.model.UserGroupMember
import java.util.*

class GroupRepositoryImpl(
    private val userRepository: UserRepository,
    private val firebaseDataSource: FirebaseDataSource
) : GroupRepository {

    override suspend fun insertUserGroup(groupName: String): Boolean =
        userRepository.fetchUser()?.let { user ->
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
                    userRepository.insertUser(user)
                } else {
                    false
                }
            }
        } ?: false

    override suspend fun insertMember(username: String, userGroup: UserGroup): Boolean =
        (firebaseDataSource.fetchUserByName(username)?.let { user ->
            val groupEntity = UserGroupMapper.fromModelToEntity(userGroup)
            groupEntity.members?.add(user.id ?: "")
            firebaseDataSource.insertUserGroup(groupEntity)
            firebaseDataSource.fetchUser(user.id ?: "")?.let { userEntity ->
                firebaseDataSource.insertUser(userEntity.apply {
                    if (selectedUserGroup == null && userGroups.size == 0) {
                        selectedUserGroup = userGroup.id
                    }
                    userGroups.add(userGroup.id)
                })
            }
        } ?: false).also {
            if (it) userRepository.fetchUser(forceCall = true)
        }

    override suspend fun updateUserGroup(userGroup: UserGroup): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMember(member: UserGroupMember): Boolean =
        (firebaseDataSource.fetchUserGroup(member.groupId)?.apply {
            members?.remove(member.userId)
        }?.run { firebaseDataSource.insertUserGroup(this) } ?: false).also {
            if (it) {
                userRepository.fetchUser(forceCall = true)
                deleteGroupFromUserId(member.userId, member.groupId)
            }
        }

    override suspend fun deleteUserGroup(userGroup: UserGroup): Boolean =
        firebaseDataSource.deleteUserGroup(userGroup.let(UserGroupMapper::fromModelToEntity)).also {
            if (it) {
                userGroup.members.forEach { member ->
                    if (member.isUserGroupAdmin) {
                        if (deleteGroupFromUserId(member.userId, userGroup.id)) {
                            userRepository.fetchUser(forceCall = true)
                        }
                    } else {
                        deleteGroupFromUserId(member.userId, userGroup.id)
                    }
                }
            }
        }

    private suspend fun deleteGroupFromUserId(userId: String, groupId: String): Boolean =
        userRepository.fetchUserById(userId)?.apply {
            userGroups.removeIf { group ->
                group.id == groupId
            }
            if (selectedUserGroup?.id == groupId) {
                selectedUserGroup = userGroups.firstOrNull()
            }
        }?.run { userRepository.insertUser(this, shouldCache = false) } ?: false
}