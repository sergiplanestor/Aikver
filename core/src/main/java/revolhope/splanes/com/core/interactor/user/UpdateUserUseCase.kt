package revolhope.splanes.com.core.interactor.user

import revolhope.splanes.com.core.data.repository.UserRepository
import revolhope.splanes.com.core.domain.model.user.User
import revolhope.splanes.com.core.interactor.BaseUseCase

class UpdateUserUseCase(
    private val userRepository: UserRepository
) : BaseUseCase<UpdateUserUseCase.Request, Boolean>() {

    override suspend fun execute(req: Request): Boolean? =
        userRepository.updateUser(user = req.user).also {
            if (it) userRepository.fetchUser(forceCall = true)
        }

    data class Request(val user: User)
}