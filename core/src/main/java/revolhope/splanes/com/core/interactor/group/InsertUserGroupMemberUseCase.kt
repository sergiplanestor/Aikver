package revolhope.splanes.com.core.interactor.group

import revolhope.splanes.com.core.data.repository.GroupRepository
import revolhope.splanes.com.core.domain.model.User
import revolhope.splanes.com.core.domain.model.UserGroup

class InsertUserGroupMemberUseCase(private val groupRepository: GroupRepository) {
    suspend operator fun invoke(member: String, group: UserGroup) =
        groupRepository.insertMember(member, group)
}