package revolhope.splanes.com.core.interactor.group

import revolhope.splanes.com.core.data.repository.GroupRepository
import revolhope.splanes.com.core.domain.model.User

class FetchUserGroupsUseCase(private val groupRepository: GroupRepository) {
    /*suspend operator fun invoke(user: User, limitTo: Int = -1) =
        groupRepository.fetchUserGroups(user, limitTo)*/
}