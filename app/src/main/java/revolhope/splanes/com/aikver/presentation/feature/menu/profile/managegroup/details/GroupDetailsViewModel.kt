package revolhope.splanes.com.aikver.presentation.feature.menu.profile.managegroup.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import revolhope.splanes.com.aikver.presentation.common.base.BaseViewModel
import revolhope.splanes.com.core.domain.model.UserGroup
import revolhope.splanes.com.core.domain.model.UserGroupMember
import revolhope.splanes.com.core.interactor.group.DeleteUserGroupMemberUseCase
import revolhope.splanes.com.core.interactor.group.DeleteUserGroupUseCase
import revolhope.splanes.com.core.interactor.group.InsertUserGroupMemberUseCase
import revolhope.splanes.com.core.interactor.user.FetchUserUseCase

class GroupDetailsViewModel(
    private val fetchUserUseCase: FetchUserUseCase,
    private val insertUserGroupMemberUseCase: InsertUserGroupMemberUseCase,
    private val deleteUserGroupMemberUseCase: DeleteUserGroupMemberUseCase,
    private val deleteUserGroupUseCase: DeleteUserGroupUseCase
) : BaseViewModel() {

    val addMemberResult: LiveData<List<UserGroupMember>> get() = _addMemberResult
    private val _addMemberResult: MutableLiveData<List<UserGroupMember>> = MutableLiveData()

    val memberDeletion: LiveData<List<UserGroupMember>> get() = _memberDeletion
    private val _memberDeletion: MutableLiveData<List<UserGroupMember>> = MutableLiveData()

    val groupDeletion: LiveData<Boolean> get() = _groupDeletion
    private val _groupDeletion: MutableLiveData<Boolean> = MutableLiveData()


    fun deleteGroup(group: UserGroup) =
        launchAsync { _groupDeletion.postValue(deleteUserGroupUseCase.invoke(group)) }

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

    fun addMember(username: String, group: UserGroup) {
        if (username.isBlank()) _addMemberResult.postValue(group.members)
        else {
            launchAsync {
                if (insertUserGroupMemberUseCase.invoke(username, group)) {
                    _addMemberResult.postValue(
                        fetchUserUseCase.invoke()?.userGroups?.find {
                            it.id == group.id
                        }?.members ?: group.members
                    )
                }
            }
        }
    }
}