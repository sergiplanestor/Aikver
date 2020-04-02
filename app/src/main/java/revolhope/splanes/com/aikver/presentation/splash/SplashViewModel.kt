package revolhope.splanes.com.aikver.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import revolhope.splanes.com.aikver.domain.User
import revolhope.splanes.com.aikver.interactor.DoLoginUseCase
import revolhope.splanes.com.aikver.interactor.GetUserUseCase
import revolhope.splanes.com.aikver.presentation.common.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.koin.core.KoinComponent
import org.koin.core.inject

class SplashViewModel : BaseViewModel(), KoinComponent {

    private val getUserUseCase: GetUserUseCase by inject()

    private val doLoginUseCase: DoLoginUseCase by inject()

    fun getUser() : LiveData<User?> {

        val liveData = MutableLiveData<User>()

        getUserUseCase.invoke(
            { user ->
                liveData.postValue(user)
            },
            errorConsumer()
        )

        return liveData
    }

    fun doLogin(user: User) : LiveData<Boolean> {

        val liveData = MutableLiveData<Boolean>()

        runBlocking(Dispatchers.IO) {
            doLoginUseCase.invoke(
                user,
                { success ->
                    liveData.postValue(success)
                },
                errorConsumer()
            )
        }

        return liveData

    }
}