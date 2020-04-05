package revolhope.splanes.com.aikver.presentation.init.login

import androidx.lifecycle.MutableLiveData
import revolhope.splanes.com.aikver.domain.User
import revolhope.splanes.com.aikver.framework.app.launchAsync
import revolhope.splanes.com.aikver.interactor.user.DoLoginUseCase
import revolhope.splanes.com.aikver.interactor.user.GetUserUseCase
import revolhope.splanes.com.aikver.interactor.user.SaveUserLocalUseCase
import revolhope.splanes.com.aikver.presentation.common.base.BaseViewModel

class LoginViewModel(
    private val storeUserUseCase: SaveUserLocalUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val doLoginUseCase: DoLoginUseCase
) : BaseViewModel() {

    private val _userLoginResult = MutableLiveData<Boolean>()
    val userLoginResult: MutableLiveData<Boolean> get() = _userLoginResult

    fun doLogin(username: String) {
        launchAsync {
            storeUserUseCase.invoke(
                username,
                { success -> if (success) fetchStoredUser(::performLogin) },
                errorConsumer()
            )
        }
    }

    private fun fetchStoredUser(callback: (User?) -> Unit) =
        launchAsync { getUserUseCase.invoke(callback, errorConsumer()) }

    private fun performLogin(user: User?) {
        launchAsync {
            if (user != null) {
                doLoginUseCase.invoke(
                    user,
                    { success -> _userLoginResult.postValue(success) },
                    errorConsumer()
                )
            } else {
                // TODO(What to do here?)
            }
        }
    }
}