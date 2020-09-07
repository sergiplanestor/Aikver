package revolhope.splanes.com.aikver.framework.net

import okhttp3.Interceptor
import okhttp3.Response
import revolhope.splanes.com.aikver.BuildConfig

class ApiInterceptor : Interceptor {

    private companion object {
        private val API_KEY: Pair<String, String> = "api_key" to BuildConfig.API_KEY
        private val LANG: Pair<String, String> = "language" to "es-ES"
    }

    override fun intercept(chain: Interceptor.Chain): Response {

        val url = chain.request()
            .url
            .newBuilder()
            .addQueryParameter(API_KEY.first, API_KEY.second)
            .addQueryParameter(LANG.first, LANG.second)
            .build()

        return chain.proceed(chain.request().newBuilder().url(url).build())
    }
}