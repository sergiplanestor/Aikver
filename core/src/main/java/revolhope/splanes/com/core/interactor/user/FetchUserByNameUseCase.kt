package revolhope.splanes.com.core.interactor.user

import revolhope.splanes.com.core.data.repository.UserRepository

class FetchUserByNameUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(username: String) = userRepository.fetchUserByName(username)
}