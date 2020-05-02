package revolhope.splanes.com.aikver.presentation.feature.menu.profile

import androidx.lifecycle.MutableLiveData
import revolhope.splanes.com.aikver.presentation.common.base.BaseViewModel
import revolhope.splanes.com.core.domain.model.user.User
import revolhope.splanes.com.core.domain.model.user.UserGroup
import revolhope.splanes.com.core.interactor.group.InsertUserGroupMemberUseCase
import revolhope.splanes.com.core.interactor.group.InsertUserGroupUseCase
import revolhope.splanes.com.core.interactor.user.FetchUserUseCase
import revolhope.splanes.com.core.interactor.user.UpdateUserUseCase

class ProfileViewModel(
    private val fetchUserUseCase: FetchUserUseCase,
    private val insertUserGroupUseCase: InsertUserGroupUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val insertUserGroupMemberUseCase: InsertUserGroupMemberUseCase
) : BaseViewModel() {

    val user: MutableLiveData<User?> get() = _user
    private val _user: MutableLiveData<User?> = MutableLiveData()

    val addMemberResult: MutableLiveData<Boolean> get() = _addMemberResult
    private val _addMemberResult: MutableLiveData<Boolean> = MutableLiveData()

    fun fetchUser() = launchAsync {
        _user.postValue(fetchUserUseCase.invoke())
    }

    fun addMember(username: String, group: UserGroup?) {
        if (username.isBlank() || group == null) {
            _addMemberResult.postValue(false)
        } else {
            launchAsync {
                _addMemberResult.postValue(insertUserGroupMemberUseCase.invoke(username, group))
            }
        }
    }

    fun addGroup(groupName: String) =
        launchAsync {
            if (insertUserGroupUseCase.invoke(groupName)) {
                fetchUserUseCase.invoke()?.let {
                    it.selectedUserGroup = it.userGroups[0]
                    if (updateUserUseCase.invoke(it)) fetchUser()
                }
            } else {
                _user.postValue(null)
            }
        }
}