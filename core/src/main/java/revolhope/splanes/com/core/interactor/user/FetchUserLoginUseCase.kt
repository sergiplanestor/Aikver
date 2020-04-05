package revolhope.splanes.com.core.interactor.user

import revolhope.splanes.com.core.data.repository.UserRepository

class FetchUserLoginUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke() = userRepository.fetchUserLogin()
}