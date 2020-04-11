package revolhope.splanes.com.core.interactor.user.avatar

import revolhope.splanes.com.core.data.repository.UserRepository

class FetchUserAvatarTypesUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke() = userRepository.fetchUserAvatarTypes()
}