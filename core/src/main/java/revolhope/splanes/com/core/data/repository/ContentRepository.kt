package revolhope.splanes.com.core.data.repository

import revolhope.splanes.com.core.domain.model.config.Configuration
import revolhope.splanes.com.core.domain.model.content.Movie
import revolhope.splanes.com.core.domain.model.content.Serie

interface ContentRepository {

    suspend fun fetchConfiguration(): Configuration?

    suspend fun searchMovies(query: String): List<Movie>?

    suspend fun searchSeries(query: String, page: Int = 1): List<Serie>?

}