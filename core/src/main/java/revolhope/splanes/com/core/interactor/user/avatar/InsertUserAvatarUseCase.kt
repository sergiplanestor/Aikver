package revolhope.splanes.com.core.interactor.user.avatar

import revolhope.splanes.com.core.data.repository.UserRepository
import revolhope.splanes.com.core.domain.model.user.UserAvatar
import revolhope.splanes.com.core.interactor.BaseUseCase

class InsertUserAvatarUseCase(
    private val userRepository: UserRepository
) : BaseUseCase<InsertUserAvatarUseCase.Request, Boolean>() {

    override suspend fun execute(req: Request): Boolean? =
        userRepository.insertUserAvatar(avatar = req.avatar)

    data class Request(val avatar: UserAvatar)
}