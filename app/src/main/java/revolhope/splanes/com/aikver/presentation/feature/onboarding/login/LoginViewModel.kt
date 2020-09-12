package revolhope.splanes.com.aikver.presentation.feature.onboarding.login

import android.content.Context
import androidx.lifecycle.MutableLiveData
import revolhope.splanes.com.aikver.presentation.common.base.BaseViewModel
import revolhope.splanes.com.core.domain.model.user.User
import revolhope.splanes.com.core.domain.model.user.UserLogin
import revolhope.splanes.com.core.domain.sha256
import revolhope.splanes.com.core.interactor.user.DoLoginUseCase
import revolhope.splanes.com.core.interactor.user.FetchUserByNameUseCase
import revolhope.splanes.com.core.interactor.user.InsertUserLoginUseCase

class LoginViewModel(
    context: Context,
    private val fetchUserByNameUseCase: FetchUserByNameUseCase,
    private val insertUserLoginUseCase: InsertUserLoginUseCase,
    private val doLoginUseCase: DoLoginUseCase
) : BaseViewModel(context) {

    private val _userLoginResult = MutableLiveData<Boolean>()
    val userLoginResult: MutableLiveData<Boolean> get() = _userLoginResult

    fun doLogin(username: String) {
        launchAsync {
            val userLogin =
                UserLogin(
                    "",
                    "${username.trim()}@xyz.com",
                    sha256(username.trim())
                )
            val isLoginSuccess = handleResponse(
                doLoginUseCase.invoke(DoLoginUseCase.Request(userLogin))
            ) ?: false
            if (isLoginSuccess) {
                handleResponse(
                    state = fetchUserByNameUseCase.invoke(
                        FetchUserByNameUseCase.Request(username.trim())
                    )
                )?.let {
                    userLogin.id = it.id
                    handleResponse(
                        state = insertUserLoginUseCase.invoke(
                            InsertUserLoginUseCase.Request(userLogin)
                        )
                    ).let { res -> _userLoginResult.postValue(res ?: false) }
                    return@launchAsync
                }
            }
            _userLoginResult.postValue(false)
        }
    }
}