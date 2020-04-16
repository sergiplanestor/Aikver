package revolhope.splanes.com.core.interactor.group

import revolhope.splanes.com.core.data.repository.GroupRepository

class InsertUserGroupUseCase(private val groupRepository: GroupRepository) {
    suspend operator fun invoke(groupName: String) =
        groupRepository.insertUserGroup(groupName)
}