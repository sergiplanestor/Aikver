package revolhope.splanes.com.core.data.repository

import revolhope.splanes.com.core.domain.model.Movie
import revolhope.splanes.com.core.domain.model.Serie

interface ContentRepository {

    suspend fun searchMovies(query: String): List<Movie>?

    suspend fun searchSeries(query: String, page: Int = 1): List<Serie>?

}