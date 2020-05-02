package revolhope.splanes.com.aikver.presentation.feature.onboarding.splash

import androidx.lifecycle.MutableLiveData
import revolhope.splanes.com.aikver.presentation.common.base.BaseViewModel
import revolhope.splanes.com.core.domain.model.user.UserLogin
import revolhope.splanes.com.core.interactor.user.DoLoginUseCase
import revolhope.splanes.com.core.interactor.user.FetchUserLoginUseCase

class SplashViewModel(
    private val fetchUserLoginUseCase: FetchUserLoginUseCase,
    private val doLoginUseCase: DoLoginUseCase
) : BaseViewModel() {

    val userLogin: MutableLiveData<UserLogin?> get() = _userLogin
    private val _userLogin = MutableLiveData<UserLogin?>()

    val userLoginResult: MutableLiveData<Boolean> get() = _userLoginResult
    private val _userLoginResult = MutableLiveData<Boolean>()

    fun getUserLogin() = launchAsync { _userLogin.postValue(fetchUserLoginUseCase.invoke()) }

    fun doLogin(user: UserLogin) =
        launchAsync { _userLoginResult.postValue(doLoginUseCase.invoke(user)) }
}