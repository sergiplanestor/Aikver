package revolhope.splanes.com.aikver.data.user

import revolhope.splanes.com.aikver.domain.User

interface UserDataSource {

    suspend fun fetchUser(
        onSuccess: (user: User?) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    )

    suspend fun saveUserLocal(
        username: String,
        onSuccess: (success: Boolean) -> Unit,
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