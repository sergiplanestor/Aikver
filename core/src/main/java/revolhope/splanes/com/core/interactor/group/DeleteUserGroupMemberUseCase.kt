package revolhope.splanes.com.core.interactor.group

import revolhope.splanes.com.core.data.repository.GroupRepository
import revolhope.splanes.com.core.domain.model.user.UserGroupMember
import revolhope.splanes.com.core.interactor.BaseUseCase

class DeleteUserGroupMemberUseCase(
    private val groupRepository: GroupRepository
) : BaseUseCase<DeleteUserGroupMemberUseCase.Request, Boolean>() {

    override suspend fun execute(req: Request): Boolean? =
        groupRepository.deleteMember(member = req.member)

    data class Request(val member: UserGroupMember)
}