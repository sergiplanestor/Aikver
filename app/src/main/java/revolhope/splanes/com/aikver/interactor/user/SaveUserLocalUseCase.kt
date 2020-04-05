package revolhope.splanes.com.aikver.interactor.user

import revolhope.splanes.com.aikver.data.user.UserRepository

class SaveUserLocalUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(
        username: String,
        onSuccess: (success: Boolean) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    ) = userRepository.saveUserLocal(username, onSuccess, onFailure)
}