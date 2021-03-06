package revolhope.splanes.com.core.domain.mapper

import revolhope.splanes.com.core.data.entity.user.UserAvatarEntity
import revolhope.splanes.com.core.data.entity.user.UserAvatarTypesEntity
import revolhope.splanes.com.core.data.entity.user.UserEntity
import revolhope.splanes.com.core.domain.model.user.User
import revolhope.splanes.com.core.domain.model.user.UserAvatar
import revolhope.splanes.com.core.domain.model.user.UserAvatarTypes
import revolhope.splanes.com.core.domain.model.user.UserGroup
import revolhope.splanes.com.core.domain.model.user.UserGroupMember

object UserMapper {

    fun fromUserModelToEntity(model: User) =
        UserEntity(
            id = model.id,
            avatar = model.avatar.let(::fromUserAvatarModelToEntity),
            username = model.username,
            selectedUserGroup = model.selectedUserGroup?.id,
            userGroups = model.userGroups.map { it.id }.toMutableList(),
            numOfContentAddedByUser = model.numOfContentAddedByUser,
            createdOn = model.createdOn
        )

    fun fromUserEntityToUserModel(entity: UserEntity, userGroups: List<UserGroup>) =
        User(
            id = entity.id ?: "",
            avatar = entity.avatar?.let(::fromUserAvatarEntityToModel) ?: UserAvatar(),
            username = entity.username ?: "",
            selectedUserGroup = userGroups.find { it.id == entity.selectedUserGroup },
            userGroups = userGroups.toMutableList(),
            numOfContentAddedByUser = entity.numOfContentAddedByUser ?: 0,
            createdOn = entity.createdOn ?: 0
        )

    fun fromUserModelToUserGroupMemberModel(
        model: User,
        groupId: String,
        userGroupAdminId: String
    ) = UserGroupMember(
            userId = model.id,
            groupId = groupId,
            avatar = model.avatar,
            username = model.username,
            isUserGroupAdmin = model.id == userGroupAdminId
        )

    fun fromUserEntityToUserGroupMemberModel(
        entity: UserEntity,
        groupId: String,
        userGroupAdminId: String
    ) = UserGroupMember(
        userId = entity.id ?: "",
        groupId = groupId,
        avatar = entity.avatar?.let(::fromUserAvatarEntityToModel) ?: UserAvatar(),
        username = entity.username ?: "",
        isUserGroupAdmin = entity.id == userGroupAdminId
    )

    fun fromUserAvatarTypesEntityToModel(entity: UserAvatarTypesEntity) =
        UserAvatarTypes(
            eyes = entity.face?.eyes ?: mutableListOf(),
            nose = entity.face?.nose ?: mutableListOf(),
            mouth = entity.face?.mouth ?: mutableListOf()
        )

    fun fromUserAvatarEntityToModel(entity: UserAvatarEntity) =
        UserAvatar(
            eyes = entity.eyes ?: "",
            nose = entity.nose ?: "",
            mouth = entity.mouth ?: "",
            color = entity.color ?: ""
        )

    fun fromUserAvatarModelToEntity(model: UserAvatar) =
        UserAvatarEntity(
            eyes = model.eyes,
            nose = model.nose,
            mouth = model.mouth,
            color = model.color
        )
}