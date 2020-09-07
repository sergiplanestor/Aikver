package revolhope.splanes.com.core.interactor.user

import revolhope.splanes.com.core.data.repository.UserRepository
import revolhope.splanes.com.core.interactor.BaseUseCase

class RegisterUserUseCase(
    private val userRepository: UserRepository
) : BaseUseCase<RegisterUserUseCase.Request, Boolean>() {

    override suspend fun execute(req: Request): Boolean? =
        userRepository.register(
            username = req.username,
            userGroupName = req.userGroupName
        )

    data class Request(
        val username: String,
        val userGroupName: String?
    )
}