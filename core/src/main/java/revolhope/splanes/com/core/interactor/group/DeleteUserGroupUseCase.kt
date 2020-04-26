package revolhope.splanes.com.core.interactor.group

import revolhope.splanes.com.core.data.repository.GroupRepository
import revolhope.splanes.com.core.domain.model.UserGroup

class DeleteUserGroupUseCase(private val groupRepository: GroupRepository) {
    suspend operator fun invoke(group: UserGroup) =
        groupRepository.deleteUserGroup(group)
}