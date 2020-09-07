package revolhope.splanes.com.core.interactor.group

import revolhope.splanes.com.core.data.repository.GroupRepository
import revolhope.splanes.com.core.domain.model.user.UserGroup
import revolhope.splanes.com.core.interactor.BaseUseCase

class InsertUserGroupMemberUseCase(
    private val groupRepository: GroupRepository
) : BaseUseCase<InsertUserGroupMemberUseCase.Request, Boolean>() {

    override suspend fun execute(req: Request): Boolean?  =
        groupRepository.insertMember(
            username = req.member,
            userGroup = req.group
        )

    data class Request(
        val member: String,
        val group: UserGroup
    )
}