package revolhope.splanes.com.core.interactor.user

import revolhope.splanes.com.core.data.repository.UserRepository

class FetchUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(force: Boolean = false) = userRepository.fetchUser(force)
}