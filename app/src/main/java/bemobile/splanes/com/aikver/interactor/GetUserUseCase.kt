package bemobile.splanes.com.aikver.interactor

import bemobile.splanes.com.aikver.data.user.UserRepository
import bemobile.splanes.com.aikver.domain.User

class GetUserUseCase(private val userRepository: UserRepository) {

    operator fun invoke(

        onSuccess: (user: User?) -> Unit,
        onFailure: (throwable: Throwable) -> Unit

    ) {
        userRepository.fetchUser(onSuccess, onFailure)
    }
}