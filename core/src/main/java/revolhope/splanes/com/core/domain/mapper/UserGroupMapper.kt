package revolhope.splanes.com.core.domain.mapper

import revolhope.splanes.com.core.data.entity.user.UserGroupEntity
import revolhope.splanes.com.core.domain.model.user.UserGroup
import revolhope.splanes.com.core.domain.model.user.UserGroupMember

object UserGroupMapper {

    fun fromModelToEntity(model: UserGroup) =
        UserGroupEntity(
            id = model.id,
            icon = model.icon,
            name = model.name,
            members = model.members.map { it.userId }.toMutableList(),
            userAdmin = model.userGroupAdmin.userId,
            dateCreation = model.dateCreation
        )

    fun fromEntityToModel(entity: UserGroupEntity, members: List<UserGroupMember>) =
        UserGroup(
            id = entity.id ?: "",
            icon = entity.icon ?: "",
            name = entity.name ?: "",
            members = members.toMutableList(),
            userGroupAdmin = members.firstOrNull { it.isUserGroupAdmin } ?: UserGroupMember.empty,
            dateCreation = entity.dateCreation ?: 0
        )

}