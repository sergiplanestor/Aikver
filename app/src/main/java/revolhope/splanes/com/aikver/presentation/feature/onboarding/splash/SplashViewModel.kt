package revolhope.splanes.com.aikver.presentation.feature.onboarding.splash

import android.content.Context
import androidx.lifecycle.MutableLiveData
import revolhope.splanes.com.aikver.presentation.common.base.BaseViewModel
import revolhope.splanes.com.core.domain.model.user.UserLogin
import revolhope.splanes.com.core.interactor.user.DoLoginUseCase
import revolhope.splanes.com.core.interactor.user.FetchUserLoginUseCase

class SplashViewModel(
    context: Context,
    private val fetchUserLoginUseCase: FetchUserLoginUseCase,
    private val doLoginUseCase: DoLoginUseCase
) : BaseViewModel(context) {

    val userLogin: MutableLiveData<UserLogin?> get() = _userLogin
    private val _userLogin = MutableLiveData<UserLogin?>()

    val userLoginResult: MutableLiveData<Boolean> get() = _userLoginResult
    private val _userLoginResult = MutableLiveData<Boolean>()

    fun getUserLogin() =
        launchAsync {
            handleResponse(
                state = fetchUserLoginUseCase.invoke(FetchUserLoginUseCase.Request),
                shouldPostError = false
            ).let { _userLogin.postValue(it) }
        }

    fun doLogin(user: UserLogin) =
        launchAsync {
            handleResponse(
                state = doLoginUseCase.invoke(DoLoginUseCase.Request(user))
            )?.let { _userLoginResult.postValue(it) }
        }
}