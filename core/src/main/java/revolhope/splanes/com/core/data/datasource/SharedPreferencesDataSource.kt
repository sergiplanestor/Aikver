package revolhope.splanes.com.core.data.datasource

interface SharedPreferencesDataSource {

    companion object {
        const val KEY_USR_LOGIN = "aikver.shared.prefs.usr"
    }

    suspend fun getString(key: String) : String?

    suspend fun putString(key: String, value: String) : Boolean
}