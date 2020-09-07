package revolhope.splanes.com.aikver.presentation.feature.menu.profile.avatar

import android.content.Context
import androidx.lifecycle.MutableLiveData
import revolhope.splanes.com.aikver.presentation.common.base.BaseViewModel
import revolhope.splanes.com.core.domain.model.user.User
import revolhope.splanes.com.core.domain.model.user.UserAvatar
import revolhope.splanes.com.core.domain.model.user.UserAvatarTypes
import revolhope.splanes.com.core.interactor.user.FetchUserUseCase
import revolhope.splanes.com.core.interactor.user.avatar.FetchUserAvatarTypesUseCase
import revolhope.splanes.com.core.interactor.user.avatar.InsertUserAvatarUseCase

class UserAvatarViewModel(
    context: Context,
    private val fetchUserUseCase: FetchUserUseCase,
    private val fetchUserAvatarTypesUseCase: FetchUserAvatarTypesUseCase,
    private val insertUserAvatarUseCase: InsertUserAvatarUseCase
) : BaseViewModel(context) {

    val user: MutableLiveData<User> get() = _user
    private val _user: MutableLiveData<User> = MutableLiveData()

    val avatarTypes: MutableLiveData<UserAvatarTypes> get() = _avatarTypes
    private val _avatarTypes: MutableLiveData<UserAvatarTypes> = MutableLiveData()

    val insertAvatarResult: MutableLiveData<Boolean> get() = _insertAvatarResult
    private val _insertAvatarResult: MutableLiveData<Boolean> = MutableLiveData()

    fun fetchUser() =
        launchAsync {
            handleResponse(
                state = fetchUserUseCase.invoke(FetchUserUseCase.Request())
            )?.let { _user.postValue(it) }
        }

    fun fetchAvatarTypes() =
        launchAsync {
            handleResponse(
                state = fetchUserAvatarTypesUseCase.invoke(FetchUserAvatarTypesUseCase.Request)
            )?.let { _avatarTypes.postValue(it) }
        }

    fun insertAvatar(avatar: UserAvatar) =
        launchAsync {
            handleResponse(
                state = insertUserAvatarUseCase.invoke(
                    InsertUserAvatarUseCase.Request(
                        avatar = avatar
                    )
                )
            )?.let { _insertAvatarResult.postValue(it) }
        }
}