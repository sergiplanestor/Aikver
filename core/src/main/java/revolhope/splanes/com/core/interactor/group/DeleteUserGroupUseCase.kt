package revolhope.splanes.com.core.interactor.group

import revolhope.splanes.com.core.data.repository.GroupRepository
import revolhope.splanes.com.core.domain.model.user.UserGroup
import revolhope.splanes.com.core.interactor.BaseUseCase

class DeleteUserGroupUseCase(
    private val groupRepository: GroupRepository
) : BaseUseCase<DeleteUserGroupUseCase.Request, Boolean>() {

    override suspend fun execute(req: Request): Boolean? =
        groupRepository.deleteUserGroup(userGroup = req.group)

    data class Request(val group: UserGroup)
}