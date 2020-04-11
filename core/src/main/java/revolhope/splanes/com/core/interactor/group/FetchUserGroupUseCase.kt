package revolhope.splanes.com.core.interactor.group

import revolhope.splanes.com.core.data.repository.GroupRepository
import revolhope.splanes.com.core.domain.model.User

class FetchUserGroupUseCase(private val groupRepository: GroupRepository) {
    /*suspend operator fun invoke(user: User, userGroupId: String) =
        groupRepository.fetchUserGroup(user, userGroupId)*/
}