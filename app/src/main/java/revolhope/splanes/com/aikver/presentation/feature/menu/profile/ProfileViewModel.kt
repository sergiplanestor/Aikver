package revolhope.splanes.com.aikver.presentation.feature.menu.profile

import androidx.lifecycle.MutableLiveData
import revolhope.splanes.com.aikver.presentation.common.base.BaseViewModel
import revolhope.splanes.com.core.domain.model.User
import revolhope.splanes.com.core.domain.model.UserGroup
import revolhope.splanes.com.core.interactor.group.InsertUserGroupMemberUseCase
import revolhope.splanes.com.core.interactor.user.FetchUserUseCase

class ProfileViewModel(
    private val fetchUserUseCase: FetchUserUseCase,
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
}