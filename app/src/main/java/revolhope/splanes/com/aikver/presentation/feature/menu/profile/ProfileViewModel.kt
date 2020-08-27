package revolhope.splanes.com.aikver.presentation.feature.menu.profile

import android.content.Context
import androidx.lifecycle.MutableLiveData
import revolhope.splanes.com.aikver.presentation.common.base.BaseViewModel
import revolhope.splanes.com.core.domain.model.user.User
import revolhope.splanes.com.core.domain.model.user.UserGroup
import revolhope.splanes.com.core.interactor.group.InsertUserGroupMemberUseCase
import revolhope.splanes.com.core.interactor.group.InsertUserGroupUseCase
import revolhope.splanes.com.core.interactor.user.FetchUserUseCase
import revolhope.splanes.com.core.interactor.user.UpdateUserUseCase

class ProfileViewModel(
    context: Context,
    private val fetchUserUseCase: FetchUserUseCase,
    private val insertUserGroupUseCase: InsertUserGroupUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val insertUserGroupMemberUseCase: InsertUserGroupMemberUseCase
) : BaseViewModel(context) {

    val user: MutableLiveData<User> get() = _user
    private val _user: MutableLiveData<User> = MutableLiveData()

    val addMemberResult: MutableLiveData<Boolean> get() = _addMemberResult
    private val _addMemberResult: MutableLiveData<Boolean> = MutableLiveData()

    fun fetchUser() =
        launchAsync {
            handleResponse(
                state = fetchUserUseCase.invoke(FetchUserUseCase.Request())
            )?.let { _user.postValue(it) }
        }

    fun addMember(username: String, group: UserGroup?) {
        if (username.isBlank() || group == null) {
            _addMemberResult.postValue(false)
        } else {
            launchAsync {
                handleResponse(
                    state = insertUserGroupMemberUseCase.invoke(
                        InsertUserGroupMemberUseCase.Request(
                            member = username,
                            group = group
                        )
                    )
                )?.let { _addMemberResult.postValue(it) }
            }
        }
    }

    fun addGroup(groupName: String) =
        launchAsync {
            handleResponse(
                state = insertUserGroupUseCase.invoke(
                    InsertUserGroupUseCase.Request(
                        groupName = groupName
                    )
                )
            )?.let { success ->
                if (success) {
                    handleResponse(
                        state = fetchUserUseCase.invoke(FetchUserUseCase.Request())
                    )?.let { user ->
                        user.selectedUserGroup = user.userGroups[0]
                        handleResponse(
                            state = updateUserUseCase.invoke(
                                UpdateUserUseCase.Request(
                                    user = user
                                )
                            )
                        )?.let { if (it) fetchUser() }
                    }
                }
            }
        }
}