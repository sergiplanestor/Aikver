package revolhope.splanes.com.core.interactor.user

import revolhope.splanes.com.core.data.repository.UserRepository
import revolhope.splanes.com.core.domain.model.user.User
import revolhope.splanes.com.core.interactor.BaseUseCase

class FetchUserUseCase(
    private val userRepository: UserRepository
) : BaseUseCase<FetchUserUseCase.Request, User>() {

    override suspend fun execute(req: Request): User? =
        userRepository.fetchUser(forceCall = req.force)

    data class Request(val force: Boolean = false)
}