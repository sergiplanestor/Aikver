package revolhope.splanes.com.aikver.framework.datasourceimpl

import android.content.Context
import android.content.SharedPreferences
import revolhope.splanes.com.core.data.datasource.SharedPreferencesDataSource
import java.lang.Exception

class SharedPreferencesDataSourceImpl(context: Context) : SharedPreferencesDataSource {

    companion object {
        private const val SHARED_NAME: String = "aikver.shared.prefs"
    }

    private val mSharedPrefs: SharedPreferences by lazy {
        context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)
    }

    override suspend fun getString(key: String) =
        try {
            mSharedPrefs.getString(key, null)
        } catch (e: Exception) {
            null
        }

    override suspend fun putString(key: String, value: String) =
        try {
            mSharedPrefs.edit().putString(key, value).apply()
            true
        } catch (e: Exception) {
            false
        }
}