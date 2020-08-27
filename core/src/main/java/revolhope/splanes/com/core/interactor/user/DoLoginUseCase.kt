package revolhope.splanes.com.core.interactor.user

import revolhope.splanes.com.core.data.repository.UserRepository
import revolhope.splanes.com.core.domain.model.user.UserLogin
import revolhope.splanes.com.core.interactor.BaseUseCase

class DoLoginUseCase(
    private val userRepository: UserRepository
) : BaseUseCase<DoLoginUseCase.Request, Boolean>() {

    override suspend fun execute(req: Request): Boolean? =
        userRepository.doLogin(userLogin = req.userLogin)

    data class Request(val userLogin: UserLogin)
}