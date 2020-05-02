package revolhope.splanes.com.core.interactor.user

import revolhope.splanes.com.core.data.repository.UserRepository
import revolhope.splanes.com.core.domain.model.user.UserLogin

class DoLoginUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(user: UserLogin) = userRepository.doLogin(user)
}