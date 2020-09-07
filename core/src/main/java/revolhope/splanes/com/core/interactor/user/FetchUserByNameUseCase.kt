package revolhope.splanes.com.core.interactor.user

import revolhope.splanes.com.core.data.repository.UserRepository
import revolhope.splanes.com.core.domain.model.user.User
import revolhope.splanes.com.core.interactor.BaseUseCase

class FetchUserByNameUseCase(
    private val userRepository: UserRepository
) : BaseUseCase<FetchUserByNameUseCase.Request, User>() {

    override suspend fun execute(req: Request): User? =
        userRepository.fetchUserByName(username = req.username)

    data class Request(val username: String)
}