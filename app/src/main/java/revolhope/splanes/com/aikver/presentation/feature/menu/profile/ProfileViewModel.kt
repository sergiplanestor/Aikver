package revolhope.splanes.com.aikver.presentation.feature.menu.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import revolhope.splanes.com.aikver.framework.app.launchAsync
import revolhope.splanes.com.core.domain.model.User
import revolhope.splanes.com.core.interactor.user.FetchUserUseCase

class ProfileViewModel(
    private val fetchUserUseCase: FetchUserUseCase
) : ViewModel() {

    val user: MutableLiveData<User?> get() = _user
    private val _user: MutableLiveData<User?> = MutableLiveData()

    fun fetchUser() = launchAsync { _user.postValue(fetchUserUseCase.invoke()) }
}