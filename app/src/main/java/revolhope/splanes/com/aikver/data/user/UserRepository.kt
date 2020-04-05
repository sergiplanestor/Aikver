package revolhope.splanes.com.aikver.data.user

import revolhope.splanes.com.aikver.domain.User

class UserRepository(private val dataSource: UserDataSource) {

    suspend fun fetchUser(
        onSuccess: (user: User?) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    ) = dataSource.fetchUser(onSuccess, onFailure)

    suspend fun saveUserLocal(
        username: String,
        onSuccess: (success: Boolean) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    ) = dataSource.saveUserLocal(username, onSuccess, onFailure)

    suspend fun insertUser(
        username: String,
        onSuccess: (success: Boolean) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    ) = dataSource.insertUser(username, onSuccess, onFailure)

    suspend fun doLogin(
        user: User,
        onSuccess: (success: Boolean) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    ) = dataSource.doLogin(user, onSuccess, onFailure)

}