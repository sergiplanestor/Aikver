package revolhope.splanes.com.core.interactor.user

import revolhope.splanes.com.core.data.repository.UserRepository

class RegisterUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(username: String, userGroupName: String?) =
        userRepository.register(username, userGroupName)
}