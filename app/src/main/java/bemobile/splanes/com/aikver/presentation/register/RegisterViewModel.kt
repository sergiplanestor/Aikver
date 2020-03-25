package bemobile.splanes.com.aikver.presentation.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import bemobile.splanes.com.aikver.interactor.RegisterUserUseCase
import bemobile.splanes.com.aikver.presentation.common.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.koin.core.KoinComponent
import org.koin.core.inject

class RegisterViewModel : BaseViewModel(), KoinComponent {

    private val registerUserUseCase: RegisterUserUseCase by inject()

    fun register(username: String?) : LiveData<Boolean> {

        val liveData = MutableLiveData<Boolean>()
        val isValid = checkField(username)

        if (isValid) {

            runBlocking(Dispatchers.IO) {
                registerUserUseCase.invoke(
                    username!!,
                    { result ->
                        liveData.postValue(result)
                    },
                    errorConsumer()
                )
            }

        } else {
            liveData.postValue(false)
        }

        return liveData
    }


    private fun checkField(value: String?) = !value.isNullOrBlank() && value.isNotEmpty()
}