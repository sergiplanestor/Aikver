package revolhope.splanes.com.aikver.presentation.feature.menu.profile.avatar

import androidx.lifecycle.MutableLiveData
import revolhope.splanes.com.aikver.framework.app.launchAsync
import revolhope.splanes.com.aikver.presentation.common.base.BaseViewModel
import revolhope.splanes.com.core.domain.model.UserAvatar
import revolhope.splanes.com.core.domain.model.UserAvatarTypes
import revolhope.splanes.com.core.interactor.user.avatar.FetchUserAvatarTypesUseCase
import revolhope.splanes.com.core.interactor.user.avatar.InsertUserAvatarUseCase

class UserAvatarViewModel(
    private val fetchUserAvatarTypesUseCase: FetchUserAvatarTypesUseCase,
    private val insertUserAvatarUseCase: InsertUserAvatarUseCase
) : BaseViewModel() {

    val avatarTypes: MutableLiveData<UserAvatarTypes?> get() = _avatarTypes
    private val _avatarTypes: MutableLiveData<UserAvatarTypes?> = MutableLiveData()

    val insertAvatarResult: MutableLiveData<Boolean> get() = _insertAvatarResult
    private val _insertAvatarResult: MutableLiveData<Boolean> = MutableLiveData()

    fun fetchAvatarTypes() =
        launchAsync { _avatarTypes.postValue(fetchUserAvatarTypesUseCase.invoke()) }

    fun insertAvatar(avatar: UserAvatar) {
        launchAsync {
            _insertAvatarResult.postValue(insertUserAvatarUseCase.invoke(avatar))
        }
    }
}