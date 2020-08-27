package revolhope.splanes.com.core.interactor.user.avatar

import revolhope.splanes.com.core.data.repository.UserRepository
import revolhope.splanes.com.core.domain.model.user.UserAvatarTypes
import revolhope.splanes.com.core.interactor.BaseUseCase

class FetchUserAvatarTypesUseCase(
    private val userRepository: UserRepository
) : BaseUseCase<FetchUserAvatarTypesUseCase.Request, UserAvatarTypes>() {

    override suspend fun execute(req: Request): UserAvatarTypes? =
        userRepository.fetchUserAvatarTypes()

    object Request
}