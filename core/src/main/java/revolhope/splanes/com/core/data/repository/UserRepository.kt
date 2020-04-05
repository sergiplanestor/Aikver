package revolhope.splanes.com.core.data.repository

import revolhope.splanes.com.core.domain.model.User
import revolhope.splanes.com.core.domain.model.UserLogin

interface UserRepository {

    suspend fun fetchUserLogin() : UserLogin?

    suspend fun insertUserLogin(userLogin: UserLogin) : Boolean

    suspend fun register(username: String, userGroupName: String?) : Boolean

    suspend fun doLogin(userLogin: UserLogin) : Boolean

    suspend fun fetchUser() : User?

    suspend fun fetchUserByName(username: String) : User?

    suspend fun insertUser(user: User) : Boolean

    suspend fun doLogout()

}