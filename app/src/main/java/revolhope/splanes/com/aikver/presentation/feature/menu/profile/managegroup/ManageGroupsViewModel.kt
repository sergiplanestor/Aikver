package revolhope.splanes.com.aikver.presentation.feature.menu.profile.managegroup

import androidx.lifecycle.MutableLiveData
import revolhope.splanes.com.aikver.framework.app.launchAsync
import revolhope.splanes.com.aikver.presentation.common.base.BaseViewModel
import revolhope.splanes.com.core.domain.model.User
import revolhope.splanes.com.core.domain.model.UserGroup
import revolhope.splanes.com.core.interactor.user.UpdateUserUseCase
import revolhope.splanes.com.core.interactor.group.InsertUserGroupUseCase
import revolhope.splanes.com.core.interactor.user.FetchUserUseCase

class ManageGroupsViewModel(
    private val fetchUserUseCase: FetchUserUseCase,
    private val insertUserGroupUseCase: InsertUserGroupUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : BaseViewModel() {

    val user: MutableLiveData<User?> get() = _user
    private val _user: MutableLiveData<User?> = MutableLiveData()

    fun fetchUser() { launchAsync { _user.postValue(fetchUserUseCase.invoke()) } }

    fun addGroup(groupName: String) {
        launchAsync {
            if (insertUserGroupUseCase.invoke(groupName)) {
                fetchUser()
            } else {
                _user.postValue(null)
            }
        }
    }

    fun changeSelectedGroup(group: UserGroup) {
        launchAsync {
            fetchUserUseCase.invoke()?.let {
                it.selectedUserGroup = group
                if(updateUserUseCase.invoke(it)) {
                    fetchUser()
                }
            } ?: _user.postValue(null)
        }
    }
}