package bemobile.splanes.com.aikver.data.user

import bemobile.splanes.com.aikver.domain.User

interface UserDataSource {

    fun fetchUser(
        onSuccess: (user: User?) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    )

    suspend fun insertUser(
        username: String,
        onSuccess: (success: Boolean) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    )

    suspend fun doLogin(
        user: User,
        onSuccess: (success: Boolean) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    )
}