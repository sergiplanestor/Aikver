package revolhope.splanes.com.core.interactor.user

import revolhope.splanes.com.core.data.repository.UserRepository
import revolhope.splanes.com.core.domain.model.user.UserLogin
import revolhope.splanes.com.core.interactor.BaseUseCase

class FetchUserLoginUseCase(
    private val userRepository: UserRepository
) : BaseUseCase<FetchUserLoginUseCase.Request, UserLogin>() {

    override suspend fun execute(req: Request): UserLogin? =
        userRepository.fetchUserLogin()

    object Request
}