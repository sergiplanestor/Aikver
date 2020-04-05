package revolhope.splanes.com.aikver.presentation.init.register

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import revolhope.splanes.com.aikver.framework.app.launchAsync
import revolhope.splanes.com.aikver.interactor.user.RegisterUserUseCase
import revolhope.splanes.com.aikver.presentation.common.base.BaseViewModel

class RegisterViewModel(private val registerUserUseCase: RegisterUserUseCase) : BaseViewModel() {

    val registerResult: MutableLiveData<Boolean> get() = _registerResult
    private val _registerResult = MutableLiveData<Boolean>()

    fun register(username: String?) {
        val isValid = checkField(username)
        if (isValid) {
            launchAsync {
                registerUserUseCase.invoke(
                    username!!,
                    { result -> _registerResult.postValue(result) },
                    errorConsumer()
                )
            }
        } else {
            _registerResult.value = false
        }
    }

    private fun checkField(value: String?) = !value.isNullOrBlank() && value.isNotEmpty()
}