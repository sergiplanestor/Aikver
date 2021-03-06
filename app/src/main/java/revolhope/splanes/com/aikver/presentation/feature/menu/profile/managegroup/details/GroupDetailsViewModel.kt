package revolhope.splanes.com.aikver.presentation.feature.menu.profile.managegroup.details

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import revolhope.splanes.com.aikver.presentation.common.base.BaseViewModel
import revolhope.splanes.com.core.domain.model.user.UserGroup
import revolhope.splanes.com.core.domain.model.user.UserGroupMember
import revolhope.splanes.com.core.interactor.group.DeleteUserGroupMemberUseCase
import revolhope.splanes.com.core.interactor.group.DeleteUserGroupUseCase
import revolhope.splanes.com.core.interactor.group.InsertUserGroupMemberUseCase
import revolhope.splanes.com.core.interactor.user.FetchUserUseCase

class GroupDetailsViewModel(
    context: Context,
    private val fetchUserUseCase: FetchUserUseCase,
    private val insertUserGroupMemberUseCase: InsertUserGroupMemberUseCase,
    private val deleteUserGroupMemberUseCase: DeleteUserGroupMemberUseCase,
    private val deleteUserGroupUseCase: DeleteUserGroupUseCase
) : BaseViewModel(context) {

    val addMemberResult: LiveData<List<UserGroupMember>> get() = _addMemberResult
    private val _addMemberResult: MutableLiveData<List<UserGroupMember>> = MutableLiveData()

    val memberDeletion: LiveData<List<UserGroupMember>> get() = _memberDeletion
    private val _memberDeletion: MutableLiveData<List<UserGroupMember>> = MutableLiveData()

    val groupDeletion: LiveData<Boolean> get() = _groupDeletion
    private val _groupDeletion: MutableLiveData<Boolean> = MutableLiveData()

    fun deleteGroup(group: UserGroup) =
        launchAsync {
            handleResponse(
                state = deleteUserGroupUseCase.invoke(
                    DeleteUserGroupUseCase.Request(
                        group = group
                    )
                )
            )?.let { _groupDeletion.postValue(it) }
        }

    fun deleteMember(member: UserGroupMember) {
        launchAsync {
            handleResponse(
                state = deleteUserGroupMemberUseCase.invoke(
                    DeleteUserGroupMemberUseCase.Request(
                        member = member
                    )
                )
            )?.let { success ->
                if (success) {
                    handleResponse(
                        state = fetchUserUseCase.invoke(FetchUserUseCase.Request())
                    )?.let { user ->
                        _memberDeletion.postValue(
                            user.userGroups.find { it.id == member.groupId }?.members
                        )
                    }
                } else {
                    _memberDeletion.postValue(mutableListOf())
                }
            }
        }
    }

    fun addMember(username: String, group: UserGroup) {
        if (username.isBlank()) _addMemberResult.postValue(group.members)
        else {
            launchAsync {
                handleResponse(
                    state = insertUserGroupMemberUseCase.invoke(
                        InsertUserGroupMemberUseCase.Request(
                            member = username,
                            group = group
                        )
                    )
                )?.let { success ->
                    if (success) {
                        handleResponse(
                            state = fetchUserUseCase.invoke(FetchUserUseCase.Request())
                        )?.let { user ->
                            _addMemberResult.postValue(
                                user.userGroups.find { it.id == group.id }?.members ?: group.members
                            )
                        }
                    }
                }
            }
        }
    }
}