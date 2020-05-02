package revolhope.splanes.com.aikver.presentation.feature.menu.profile.managegroup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import revolhope.splanes.com.aikver.presentation.common.base.BaseViewModel
import revolhope.splanes.com.core.domain.model.user.User
import revolhope.splanes.com.core.domain.model.user.UserGroup
import revolhope.splanes.com.core.interactor.user.UpdateUserUseCase
import revolhope.splanes.com.core.interactor.group.InsertUserGroupUseCase
import revolhope.splanes.com.core.interactor.user.FetchUserUseCase

class ManageGroupsViewModel(
    private val fetchUserUseCase: FetchUserUseCase,
    private val insertUserGroupUseCase: InsertUserGroupUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : BaseViewModel() {

    val user: LiveData<User?> get() = _user
    private val _user: MutableLiveData<User?> = MutableLiveData()

    val isUserAdminOf: LiveData<Pair<UserGroup, String>> get() = _isUserAdminOf
    private val _isUserAdminOf: MutableLiveData<Pair<UserGroup, String>> = MutableLiveData()

    fun fetchUser(forceCall: Boolean = false) =
        launchAsync { _user.postValue(fetchUserUseCase.invoke(forceCall)) }

    fun addGroup(groupName: String) =
        launchAsync {
            if (insertUserGroupUseCase.invoke(groupName)) {
                fetchUser()
            } else {
                _user.postValue(null)
            }
        }

    fun changeSelectedGroup(group: UserGroup) =
        launchAsync {
            fetchUserUseCase.invoke()?.let {
                it.selectedUserGroup = group
                if (updateUserUseCase.invoke(it)) {
                    fetchUser()
                }
            } ?: _user.postValue(null)
        }

    fun isUserAdminOf(group: UserGroup) =
        launchAsync {
            _isUserAdminOf.postValue(
                group to (fetchUserUseCase.invoke()?.id ?: "")
            )
        }
}