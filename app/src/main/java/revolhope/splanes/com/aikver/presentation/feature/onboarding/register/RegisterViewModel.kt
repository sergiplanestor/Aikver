package revolhope.splanes.com.aikver.presentation.feature.onboarding.register

import androidx.lifecycle.MutableLiveData
import revolhope.splanes.com.aikver.presentation.common.base.BaseViewModel
import revolhope.splanes.com.core.domain.model.user.User
import revolhope.splanes.com.core.interactor.user.FetchUserUseCase
import revolhope.splanes.com.core.interactor.user.RegisterUserUseCase

class RegisterViewModel(
    private val registerUserUseCase: RegisterUserUseCase,
    private val fetchUserUseCase: FetchUserUseCase
) : BaseViewModel() {

    val registerResult: MutableLiveData<Boolean> get() = _registerResult
    private val _registerResult = MutableLiveData<Boolean>()

    val user: MutableLiveData<User?> get() = _user
    private val _user = MutableLiveData<User?>()

    fun register(username: String?, userGroup: String?) {
        if (!username.isNullOrBlank()) {
            launchAsync {
                _registerResult.postValue(registerUserUseCase.invoke(username, userGroup))
            }
        } else {
            _registerResult.value = false
        }
    }

    fun getUser() {
        launchAsync {
            _user.postValue(fetchUserUseCase.invoke())
        }
    }
}