package revolhope.splanes.com.core.data.repository

import revolhope.splanes.com.core.domain.model.user.User
import revolhope.splanes.com.core.domain.model.user.UserAvatar
import revolhope.splanes.com.core.domain.model.user.UserAvatarTypes
import revolhope.splanes.com.core.domain.model.user.UserLogin

interface UserRepository {

    suspend fun fetchUserLogin() : UserLogin?

    suspend fun insertUserLogin(userLogin: UserLogin) : Boolean

    suspend fun register(username: String, userGroupName: String?) : Boolean

    suspend fun doLogin(userLogin: UserLogin) : Boolean

    suspend fun fetchUser(forceCall: Boolean = false) : User?

    suspend fun fetchUserByName(username: String) : User?

    suspend fun fetchUserById(userId: String) : User?

    suspend fun fetchUserAvatarTypes() : UserAvatarTypes?

    suspend fun insertUserAvatar(avatar: UserAvatar) : Boolean

    suspend fun insertUser(user: User, shouldCache: Boolean = true) : Boolean

    suspend fun updateUser(user: User) : Boolean

    suspend fun doLogout()

}