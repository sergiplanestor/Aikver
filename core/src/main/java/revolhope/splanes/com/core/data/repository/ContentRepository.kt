package revolhope.splanes.com.core.data.repository

interface ContentRepository {

    suspend fun searchMovies(query: String)

    suspend fun searchSeries(query: String)

}