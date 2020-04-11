package revolhope.splanes.com.aikver.presentation.feature.menu.profile.avatar

import androidx.lifecycle.MutableLiveData
import revolhope.splanes.com.aikver.framework.app.launchAsync
import revolhope.splanes.com.aikver.presentation.common.base.BaseViewModel
import revolhope.splanes.com.core.domain.model.User
import revolhope.splanes.com.core.domain.model.UserAvatar
import revolhope.splanes.com.core.domain.model.UserAvatarTypes
import revolhope.splanes.com.core.interactor.user.FetchUserUseCase
import revolhope.splanes.com.core.interactor.user.avatar.FetchUserAvatarTypesUseCase
import revolhope.splanes.com.core.interactor.user.avatar.InsertUserAvatarUseCase

class UserAvatarViewModel(
    private val fetchUserUseCase: FetchUserUseCase,
    private val fetchUserAvatarTypesUseCase: FetchUserAvatarTypesUseCase,
    private val insertUserAvatarUseCase: InsertUserAvatarUseCase
) : BaseViewModel() {

    val user: MutableLiveData<User?> get() = _user
    private val _user: MutableLiveData<User?> = MutableLiveData()

    val avatarTypes: MutableLiveData<UserAvatarTypes?> get() = _avatarTypes
    private val _avatarTypes: MutableLiveData<UserAvatarTypes?> = MutableLiveData()

    val insertAvatarResult: MutableLiveData<Boolean> get() = _insertAvatarResult
    private val _insertAvatarResult: MutableLiveData<Boolean> = MutableLiveData()

    fun fetchUser() = launchAsync { _user.postValue(fetchUserUseCase.invoke()) }

    fun fetchAvatarTypes() =
        launchAsync { _avatarTypes.postValue(fetchUserAvatarTypesUseCase.invoke()) }

    fun insertAvatar(avatar: UserAvatar) {
        launchAsync { _insertAvatarResult.postValue(insertUserAvatarUseCase.invoke(avatar)) }
    }
}