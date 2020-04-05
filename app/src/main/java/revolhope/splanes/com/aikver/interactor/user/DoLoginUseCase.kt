package revolhope.splanes.com.aikver.interactor.user

import revolhope.splanes.com.aikver.data.user.UserRepository
import revolhope.splanes.com.aikver.domain.User

class DoLoginUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(
        user: User,
        onSuccess: (success: Boolean) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    ) = userRepository.doLogin(user, onSuccess, onFailure)
}