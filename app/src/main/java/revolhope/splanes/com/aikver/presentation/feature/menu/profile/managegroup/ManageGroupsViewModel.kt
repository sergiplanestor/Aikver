package revolhope.splanes.com.aikver.presentation.feature.menu.profile.managegroup

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import revolhope.splanes.com.aikver.presentation.common.base.BaseViewModel
import revolhope.splanes.com.core.domain.model.user.User
import revolhope.splanes.com.core.domain.model.user.UserGroup
import revolhope.splanes.com.core.interactor.user.UpdateUserUseCase
import revolhope.splanes.com.core.interactor.group.InsertUserGroupUseCase
import revolhope.splanes.com.core.interactor.user.FetchUserUseCase

class ManageGroupsViewModel(
    context: Context,
    private val fetchUserUseCase: FetchUserUseCase,
    private val insertUserGroupUseCase: InsertUserGroupUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : BaseViewModel(context) {

    val user: LiveData<User> get() = _user
    private val _user: MutableLiveData<User> = MutableLiveData()

    val isUserAdminOf: LiveData<Pair<UserGroup, String>> get() = _isUserAdminOf
    private val _isUserAdminOf: MutableLiveData<Pair<UserGroup, String>> = MutableLiveData()

    fun fetchUser(forceCall: Boolean = false) =
        launchAsync {
            handleResponse(
                state = fetchUserUseCase.invoke(
                    FetchUserUseCase.Request(
                        force = forceCall
                    )
                )
            )?.let { _user.postValue(it) }
        }

    fun addGroup(groupName: String) =
        launchAsync {
            handleResponse(
                state = insertUserGroupUseCase.invoke(
                    InsertUserGroupUseCase.Request(
                        groupName = groupName
                    )
                )
            )?.let { success -> if (success) fetchUser() }
        }

    fun changeSelectedGroup(group: UserGroup) =
        launchAsync {
            handleResponse(
                state = fetchUserUseCase.invoke(FetchUserUseCase.Request())
            )?.let { user ->
                user.selectedUserGroup = group
                handleResponse(
                    state = updateUserUseCase.invoke(
                        UpdateUserUseCase.Request(
                            user = user
                        )
                    )
                )?.let { success -> if (success) fetchUser() }
            }
        }

    fun isUserAdminOf(group: UserGroup) =
        launchAsync {
            handleResponse(
                state = fetchUserUseCase.invoke(FetchUserUseCase.Request())
            )?.let { _isUserAdminOf.postValue(group to it.id) }
        }
}