package revolhope.splanes.com.aikver.framework.repositoryimpl
/*

import revolhope.splanes.com.aikver.framework.helper.FirebaseHelper
import revolhope.splanes.com.aikver.framework.helper.SharedPreferencesHelper
import revolhope.splanes.com.core.data.datasource.UserDataSource
import revolhope.splanes.com.core.domain.helper.CryptoHelper
import revolhope.splanes.com.core.domain.model.User
import revolhope.splanes.com.core.domain.model.UserGroup

class UserService(
    private val sharedPreferencesHelper: SharedPreferencesHelper,
    private val firebaseHelper: FirebaseHelper
) : UserDataSource {

    override suspend fun fetchUser(
        onSuccess: (user: User?) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    ) {
        val username = sharedPreferencesHelper.getString(
            key = SharedPreferencesHelper.PREF_USR
        )
        val pwd = sharedPreferencesHelper.getString(
            key = SharedPreferencesHelper.PREF_PWD
        )

        if (username != null && pwd != null) onSuccess(User(username = username, pwd = pwd))
        else onSuccess(null)
    }

    override suspend fun saveUserLocal(
        username: String,
        onSuccess: (success: Boolean) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    ) {
        saveUserSharedPreferences(username, CryptoHelper.sha256(username))
        onSuccess(true)
    }

    override suspend fun insertUser(
        username: String,
        userGroup: String?,
        onSuccess: (success: Boolean) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    ) {
        val pwd = CryptoHelper.sha256(username)
        firebaseHelper.createUser(
            username = username,
            password = pwd,
            onSuccess = { success ->
                if (success) {
                    saveUserSharedPreferences(username, pwd)
                    if (!userGroup.isNullOrBlank()) {
                        firebaseHelper.insertUserGroup(
                            UserGroup(
                                name = userGroup,
                                members = mutableListOf<User>().apply { add(User(username, pwd)) },
                                userCreator = User(username = username, pwd = pwd)
                            ),
                            onSuccess,
                            onFailure
                        )
                    }
                } else {
                    onSuccess(false)
                }
            },
            onError = onFailure
        )
    }

    override suspend fun doLogin(
        user: User,
        onSuccess: (success: Boolean) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    ) {
        firebaseHelper.signIn(
            username = user.username,
            password = user.pwd,
            onSuccess = onSuccess,
            onError = onFailure
        )
    }

    private fun saveUserSharedPreferences(username: String, pwd: String) {
        sharedPreferencesHelper.putString(
            key = SharedPreferencesHelper.PREF_USR,
            value = username
        )
        sharedPreferencesHelper.putString(
            key = SharedPreferencesHelper.PREF_PWD,
            value = pwd
        )
    }
}*/
