package bemobile.splanes.com.aikver.interactor

import bemobile.splanes.com.aikver.data.user.UserRepository

class RegisterUserUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(

        username: String,
        onSuccess: (success: Boolean) -> Unit,
        onFailure: (throwable: Throwable) -> Unit

    ) = userRepository.insertUser(username, onSuccess, onFailure)
}