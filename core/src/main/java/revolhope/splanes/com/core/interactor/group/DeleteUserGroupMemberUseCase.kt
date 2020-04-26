package revolhope.splanes.com.core.interactor.group

import revolhope.splanes.com.core.data.repository.GroupRepository
import revolhope.splanes.com.core.domain.model.UserGroupMember

class DeleteUserGroupMemberUseCase(private val groupRepository: GroupRepository) {
    suspend operator fun invoke(member: UserGroupMember) =
        groupRepository.deleteMember(member)
}