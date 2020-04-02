package revolhope.splanes.com.aikver.interactor

import revolhope.splanes.com.aikver.data.user.UserRepository
import revolhope.splanes.com.aikver.domain.User

class GetUserUseCase(private val userRepository: UserRepository) {

    operator fun invoke(

        onSuccess: (user: User?) -> Unit,
        onFailure: (throwable: Throwable) -> Unit

    ) {
        userRepository.fetchUser(onSuccess, onFailure)
    }
}