package revolhope.splanes.com.aikver.presentation.init.splash

import androidx.lifecycle.MutableLiveData
import revolhope.splanes.com.aikver.domain.User
import revolhope.splanes.com.aikver.framework.app.launchAsync
import revolhope.splanes.com.aikver.interactor.user.DoLoginUseCase
import revolhope.splanes.com.aikver.interactor.user.GetUserUseCase
import revolhope.splanes.com.aikver.presentation.common.base.BaseViewModel

class SplashViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val doLoginUseCase: DoLoginUseCase
) : BaseViewModel() {

    val user: MutableLiveData<User?> get() = _user
    private val _user = MutableLiveData<User?>()

    val userLoginResult: MutableLiveData<Boolean> get() = _userLoginResult
    private val _userLoginResult = MutableLiveData<Boolean>()

    fun getUser() {
        launchAsync {
            getUserUseCase.invoke(
                { user -> _user.postValue(user) },
                errorConsumer()
            )
        }
    }

    fun doLogin(user: User) {
        launchAsync {
            doLoginUseCase.invoke(
                user,
                { success -> _userLoginResult.postValue(success) },
                errorConsumer()
            )
        }
    }
}