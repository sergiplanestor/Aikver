package revolhope.splanes.com.core.interactor.group

import revolhope.splanes.com.core.data.repository.GroupRepository
import revolhope.splanes.com.core.interactor.BaseUseCase

class InsertUserGroupUseCase(
    private val groupRepository: GroupRepository
) : BaseUseCase<InsertUserGroupUseCase.Request, Boolean>() {

    override suspend fun execute(req: Request): Boolean? =
        groupRepository.insertUserGroup(groupName = req.groupName)

    data class Request(val groupName: String)
}