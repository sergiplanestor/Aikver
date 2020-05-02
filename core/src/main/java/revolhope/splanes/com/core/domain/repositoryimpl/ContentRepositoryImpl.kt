package revolhope.splanes.com.core.domain.repositoryimpl


import revolhope.splanes.com.core.data.datasource.ApiDataSource
import revolhope.splanes.com.core.data.datasource.CacheConfigurationDataSource
import revolhope.splanes.com.core.data.repository.ContentRepository
import revolhope.splanes.com.core.domain.mapper.ConfigurationMapper
import revolhope.splanes.com.core.domain.mapper.ContentMapper
import revolhope.splanes.com.core.domain.model.config.Configuration
import revolhope.splanes.com.core.domain.model.content.movie.Movie
import revolhope.splanes.com.core.domain.model.content.serie.Serie

class ContentRepositoryImpl(private val apiDataSource: ApiDataSource) : ContentRepository {

    override suspend fun fetchConfiguration(): Configuration? =
        if (CacheConfigurationDataSource.fetchConfig() == null) {
            apiDataSource.fetchConfiguration()?.let(ConfigurationMapper::fromEntityToModel)
                .also(CacheConfigurationDataSource::insertConfig)
        } else {
            CacheConfigurationDataSource.fetchConfig()
        }

    override suspend fun searchMovies(query: String): List<Movie>? =
        apiDataSource.searchMovies(query = query)?.let {
            it.results?.let{ movies ->
                ContentMapper.fromMovieEntityListToModel(movies, fetchConfiguration())
            }
        }

    override suspend fun searchSeries(query: String, page: Int): List<Serie>? =
        apiDataSource.searchSeries(query = query, page = page)?.let {
            it.results?.let{ series ->
                ContentMapper.fromSerieEntityListToModel(series, fetchConfiguration())
            }
        }
}
