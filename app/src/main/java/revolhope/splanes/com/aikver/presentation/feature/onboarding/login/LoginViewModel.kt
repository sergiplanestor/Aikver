package revolhope.splanes.com.aikver.presentation.feature.onboarding.login

import androidx.lifecycle.MutableLiveData
import revolhope.splanes.com.aikver.presentation.common.base.BaseViewModel
import revolhope.splanes.com.core.domain.model.user.User
import revolhope.splanes.com.core.domain.model.user.UserLogin
import revolhope.splanes.com.core.domain.sha256
import revolhope.splanes.com.core.interactor.user.DoLoginUseCase
import revolhope.splanes.com.core.interactor.user.FetchUserByNameUseCase
import revolhope.splanes.com.core.interactor.user.InsertUserLoginUseCase

class LoginViewModel(
    private val fetchUserByNameUseCase: FetchUserByNameUseCase,
    private val insertUserLoginUseCase: InsertUserLoginUseCase,
    private val doLoginUseCase: DoLoginUseCase
) : BaseViewModel() {

    private val _userLoginResult = MutableLiveData<Boolean>()
    val userLoginResult: MutableLiveData<Boolean> get() = _userLoginResult

    private var user: User? = null

    fun doLogin(username: String) {
        launchAsync {
            val userLogin =
                UserLogin(
                    "",
                    "$username@xyz.com",
                    sha256(username)
                )
            if (doLoginUseCase.invoke(userLogin)) {
                user = fetchUserByNameUseCase.invoke(username)
                if (user != null) {
                    userLogin.id = user!!.id
                    _userLoginResult.postValue(insertUserLoginUseCase.invoke(userLogin))
                    return@launchAsync
                }
            }
            _userLoginResult.postValue(false)
        }
    }

    fun getUser() = user!!
}