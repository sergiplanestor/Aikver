package bemobile.splanes.com.aikver.framework.service

import bemobile.splanes.com.aikver.data.user.UserDataSource
import bemobile.splanes.com.aikver.domain.User
import bemobile.splanes.com.aikver.framework.helper.CryptoHelper
import bemobile.splanes.com.aikver.framework.helper.FirebaseHelper
import bemobile.splanes.com.aikver.framework.helper.SharedPreferencesHelper

class UserService(
    private val sharedPreferencesHelper: SharedPreferencesHelper,
    private val firebaseHelper: FirebaseHelper
) : UserDataSource {

    override fun fetchUser(
        onSuccess: (user: User?) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    ) {
        val username = sharedPreferencesHelper.getString(
            key = SharedPreferencesHelper.PREF_USR
        )
        val password = sharedPreferencesHelper.getString(
            key = SharedPreferencesHelper.PREF_PWD
        )

        if (username != null && password != null) {

            onSuccess(User(username, password))

        } else {

            onSuccess(null)
        }
    }

    override suspend fun insertUser(
        username: String,
        onSuccess: (success: Boolean) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    ) {
        val pwd = CryptoHelper.sha256(username)
        firebaseHelper.createUser(
            username = username,
            password = pwd,
            onSuccess = { success ->
                if (success) {
                    sharedPreferencesHelper.putString(
                        key = SharedPreferencesHelper.PREF_USR,
                        value = username
                    )
                    sharedPreferencesHelper.putString(
                        key = SharedPreferencesHelper.PREF_PWD,
                        value = pwd
                    )
                }
                onSuccess(success)
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
            onError = onFailure)
    }
}