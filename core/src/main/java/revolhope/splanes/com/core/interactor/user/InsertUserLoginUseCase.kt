package revolhope.splanes.com.core.interactor.user

import revolhope.splanes.com.core.data.repository.UserRepository
import revolhope.splanes.com.core.domain.model.UserLogin


class InsertUserLoginUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(userLogin: UserLogin) = userRepository.insertUserLogin(userLogin)
}