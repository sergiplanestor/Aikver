package revolhope.splanes.com.aikver.interactor.user

import revolhope.splanes.com.aikver.data.user.UserRepository

class RegisterUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(
        username: String,
        onSuccess: (success: Boolean) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    ) = userRepository.insertUser(username, onSuccess, onFailure)
}