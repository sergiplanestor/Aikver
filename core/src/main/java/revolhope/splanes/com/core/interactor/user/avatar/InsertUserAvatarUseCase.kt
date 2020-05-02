package revolhope.splanes.com.core.interactor.user.avatar

import revolhope.splanes.com.core.data.repository.UserRepository
import revolhope.splanes.com.core.domain.model.user.UserAvatar

class InsertUserAvatarUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(avatar: UserAvatar) = userRepository.insertUserAvatar(avatar)
}