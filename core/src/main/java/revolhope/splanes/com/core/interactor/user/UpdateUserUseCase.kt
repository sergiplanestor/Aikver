package revolhope.splanes.com.core.interactor.user

import revolhope.splanes.com.core.data.repository.UserRepository
import revolhope.splanes.com.core.domain.model.User

class UpdateUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(user: User) =
        userRepository.updateUser(user).also { if (it) userRepository.fetchUser(forceCall = true) }
}