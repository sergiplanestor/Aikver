package revolhope.splanes.com.aikver.framework.net

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import revolhope.splanes.com.aikver.BuildConfig

object ApiClientFactory {

    fun <T> create(clazz: Class<T>): T {

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(ApiInterceptor())
            .build()

        return Retrofit.Builder()
            .baseUrl("https://${BuildConfig.API_BASE_URL}")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(clazz)
    }

}