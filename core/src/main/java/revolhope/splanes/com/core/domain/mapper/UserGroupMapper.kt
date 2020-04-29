package revolhope.splanes.com.core.domain.mapper

import revolhope.splanes.com.core.data.entity.user.UserGroupEntity
import revolhope.splanes.com.core.domain.model.UserGroup
import revolhope.splanes.com.core.domain.model.UserGroupMember

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
            userGroupAdmin = members.first { it.isUserGroupAdmin }, /* TODO: This should throw exception... check it */
            dateCreation = entity.dateCreation ?: 0
        )

}