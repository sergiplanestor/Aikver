package bemobile.splanes.com.aikver.interactor

import bemobile.splanes.com.aikver.data.user.UserRepository
import bemobile.splanes.com.aikver.domain.User

class DoLoginUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(
        user: User,
        onSuccess: (success: Boolean) -> Unit,
        onFailure: (throwable: Throwable) -> Unit

    ) {
        userRepository.doLogin(user, onSuccess, onFailure)
    }
}