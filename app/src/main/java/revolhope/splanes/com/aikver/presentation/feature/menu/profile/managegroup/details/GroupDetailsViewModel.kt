package revolhope.splanes.com.aikver.presentation.feature.menu.profile.managegroup.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import revolhope.splanes.com.aikver.presentation.common.base.BaseViewModel
import revolhope.splanes.com.core.domain.model.UserGroupMember
import revolhope.splanes.com.core.interactor.group.DeleteUserGroupMemberUseCase
import revolhope.splanes.com.core.interactor.user.FetchUserUseCase

class GroupDetailsViewModel(
    private val fetchUserUseCase: FetchUserUseCase,
    private val deleteUserGroupMemberUseCase: DeleteUserGroupMemberUseCase
) : BaseViewModel() {

    val memberDeletion: LiveData<List<UserGroupMember>> get() = _memberDeletion
    private val _memberDeletion: MutableLiveData<List<UserGroupMember>> = MutableLiveData()

    fun deleteMember(member: UserGroupMember) {
        launchAsync {
            if (deleteUserGroupMemberUseCase.invoke(member)) {
                _memberDeletion.postValue(
                    fetchUserUseCase.invoke()?.userGroups?.find { it.id == member.groupId }?.members
                )
            } else {
                _memberDeletion.postValue(mutableListOf())
            }
        }
    }
}