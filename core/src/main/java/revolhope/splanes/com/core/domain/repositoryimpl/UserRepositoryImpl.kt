package revolhope.splanes.com.core.domain.repositoryimpl

import com.google.gson.Gson
import revolhope.splanes.com.core.data.datasource.FirebaseDataSource
import revolhope.splanes.com.core.data.datasource.SharedPreferencesDataSource
import revolhope.splanes.com.core.data.repository.GroupRepository
import revolhope.splanes.com.core.data.repository.UserRepository
import revolhope.splanes.com.core.domain.helper.CryptoHelper
import revolhope.splanes.com.core.domain.model.User
import revolhope.splanes.com.core.domain.model.UserGroup
import revolhope.splanes.com.core.domain.model.UserLogin
import java.lang.Exception
import java.util.*

class UserRepositoryImpl(
    private val firebaseDataSource: FirebaseDataSource,
    private val sharedPreferencesDataSource: SharedPreferencesDataSource,
    private val groupRepository: GroupRepository
) : UserRepository {

    override suspend fun fetchUserLogin(): UserLogin? {
        val value =
            sharedPreferencesDataSource.getString(SharedPreferencesDataSource.KEY_USR_LOGIN)
        return try {
            Gson().fromJson(value, UserLogin::class.java)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun insertUserLogin(userLogin: UserLogin): Boolean =
        sharedPreferencesDataSource.putString(
            SharedPreferencesDataSource.KEY_USR_LOGIN,
            Gson().toJson(userLogin)
        )

    override suspend fun register(username: String, userGroupName: String?): Boolean {
        val userLogin = UserLogin(
            id = UUID.randomUUID().toString().replace("-", ""),
            email = "$username@xyz.com",
            pwd = CryptoHelper.sha256(username)
        )
        val resultRegister = firebaseDataSource.register(userLogin.email, userLogin.pwd)
        return if (resultRegister) {
            val resultInsertUserLogin = insertUserLogin(userLogin)
            val user = User(userLogin.id, username)
            if (!userGroupName.isNullOrBlank()) {
                val userGroup = UserGroup(
                    id = UUID.randomUUID().toString().replace("-", ""),
                    name = userGroupName,
                    members = mutableListOf(user.id),
                    userCreator = user.id
                )
                val resultStoreUser = insertUser(user.apply { userGroups.add(userGroup.id) })
                val resultStoreUserGroup = groupRepository.insertUserGroup(userGroup)
                resultInsertUserLogin && resultStoreUser && resultStoreUserGroup
            } else {
                insertUser(user)
            }
        } else {
            false
        }
    }

    override suspend fun doLogin(userLogin: UserLogin) =
        firebaseDataSource.login(userLogin.email, userLogin.pwd)

    override suspend fun fetchUser(): User? =
        firebaseDataSource.fetchUser(fetchUserLogin()?.id ?: "")

    override suspend fun fetchUserByName(username: String): User? =
        firebaseDataSource.fetchUserByName(username)

    override suspend fun insertUser(user: User) = firebaseDataSource.insertUser(user)

    override suspend fun doLogout() = firebaseDataSource.logout()
}