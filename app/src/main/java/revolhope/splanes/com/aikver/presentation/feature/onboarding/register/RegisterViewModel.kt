package revolhope.splanes.com.aikver.presentation.feature.onboarding.register

import android.content.Context
import androidx.lifecycle.MutableLiveData
import revolhope.splanes.com.aikver.presentation.common.base.BaseViewModel
import revolhope.splanes.com.core.domain.model.user.User
import revolhope.splanes.com.core.interactor.user.FetchUserUseCase
import revolhope.splanes.com.core.interactor.user.RegisterUserUseCase

class RegisterViewModel(
    context: Context,
    private val registerUserUseCase: RegisterUserUseCase,
    private val fetchUserUseCase: FetchUserUseCase
) : BaseViewModel(context) {

    val registerResult: MutableLiveData<Boolean> get() = _registerResult
    private val _registerResult = MutableLiveData<Boolean>()

    val user: MutableLiveData<User?> get() = _user
    private val _user = MutableLiveData<User?>()

    fun register(username: String?, userGroup: String?) {
        if (!username.isNullOrBlank()) {
            launchAsync {
                handleResponse(
                    state = registerUserUseCase.invoke(
                        RegisterUserUseCase.Request(username, userGroup)
                    )
                )?.let { _registerResult.postValue(it) }
            }
        } else {
            _registerResult.value = false
        }
    }

    fun getUser() {
        launchAsync {
            handleResponse(
                state = fetchUserUseCase.invoke(FetchUserUseCase.Request())
            )?.let { _user.postValue(it) }
        }
    }
}