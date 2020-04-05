package revolhope.splanes.com.core.data.datasource

import revolhope.splanes.com.core.domain.model.User
import revolhope.splanes.com.core.domain.model.UserGroup

interface FirebaseDataSource {
    suspend fun login(email: String, pwd: String) : Boolean

    suspend fun register(email: String, pwd: String) : Boolean

    suspend fun insertUser(user: User) : Boolean

    suspend fun fetchUser(id: String) : User?

    suspend fun fetchUserByName(username: String) : User?

    suspend fun insertUserGroup(userGroup: UserGroup) : Boolean

    suspend fun logout()
}