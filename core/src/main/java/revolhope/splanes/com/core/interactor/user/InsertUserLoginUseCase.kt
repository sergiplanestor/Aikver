package revolhope.splanes.com.core.interactor.user

import revolhope.splanes.com.core.data.repository.UserRepository
import revolhope.splanes.com.core.domain.model.user.UserLogin
import revolhope.splanes.com.core.interactor.BaseUseCase


class InsertUserLoginUseCase(
    private val userRepository: UserRepository
) : BaseUseCase<InsertUserLoginUseCase.Request, Boolean>() {

    override suspend fun execute(req: Request): Boolean? =
        userRepository.insertUserLogin(userLogin = req.userLogin)

    data class Request(val userLogin: UserLogin)
}