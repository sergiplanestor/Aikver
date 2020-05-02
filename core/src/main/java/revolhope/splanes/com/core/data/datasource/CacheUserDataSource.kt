package revolhope.splanes.com.core.data.datasource

import revolhope.splanes.com.core.domain.model.user.User

object CacheUserDataSource {

    private var user: User? = null

    fun insertUser(user: User?) {
        this.user = user
    }

    fun fetchUser() = user

    fun clear() { user = null }

}