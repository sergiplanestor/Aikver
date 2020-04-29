package revolhope.splanes.com.core.domain.repositoryimpl


import revolhope.splanes.com.core.data.datasource.ApiDataSource
import revolhope.splanes.com.core.data.repository.ContentRepository
import revolhope.splanes.com.core.domain.mapper.ContentMapper
import revolhope.splanes.com.core.domain.model.Movie
import revolhope.splanes.com.core.domain.model.Serie

class ContentRepositoryImpl(private val apiDataSource: ApiDataSource) : ContentRepository {

    override suspend fun searchMovies(query: String): List<Movie>? =
        apiDataSource.searchMovies(query = query)?.let {
            it.results?.let(ContentMapper::fromMovieEntityListToModel)
        }

    override suspend fun searchSeries(query: String, page: Int): List<Serie>? =
        apiDataSource.searchSeries(query = query, page = page)?.let {
            it.results?.let(ContentMapper::fromSerieEntityListToModel)
        }
}
