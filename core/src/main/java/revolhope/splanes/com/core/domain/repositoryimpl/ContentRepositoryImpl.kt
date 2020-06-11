package revolhope.splanes.com.core.domain.repositoryimpl


import revolhope.splanes.com.core.data.datasource.ApiDataSource
import revolhope.splanes.com.core.data.datasource.CacheConfigurationDataSource
import revolhope.splanes.com.core.data.datasource.FirebaseDataSource
import revolhope.splanes.com.core.data.repository.ContentRepository
import revolhope.splanes.com.core.data.repository.UserRepository
import revolhope.splanes.com.core.domain.mapper.ConfigurationMapper
import revolhope.splanes.com.core.domain.mapper.ContentMapper
import revolhope.splanes.com.core.domain.mapper.UserGroupMapper
import revolhope.splanes.com.core.domain.model.config.Configuration
import revolhope.splanes.com.core.domain.model.content.Network
import revolhope.splanes.com.core.domain.model.content.movie.Movie
import revolhope.splanes.com.core.domain.model.content.serie.Serie
import revolhope.splanes.com.core.domain.model.content.serie.SerieDetails

class ContentRepositoryImpl(
    private val apiDataSource: ApiDataSource,
    private val firebaseDataSource: FirebaseDataSource,
    private val userRepository: UserRepository
) : ContentRepository {

    override suspend fun fetchConfiguration(): Configuration? =
        if (CacheConfigurationDataSource.fetchConfig() == null) {
            apiDataSource.fetchConfiguration()?.let(ConfigurationMapper::fromEntityToModel)
                .also(CacheConfigurationDataSource::insertConfig)
        } else {
            CacheConfigurationDataSource.fetchConfig()
        }

    override suspend fun searchMovies(query: String): List<Movie>? =
        apiDataSource.searchMovies(query = query)?.let {
            it.results?.let { movies ->
                ContentMapper.fromMovieEntityListToModel(movies, fetchConfiguration())
            }
        }

    override suspend fun searchSeries(query: String, page: Int): List<Serie>? =
        apiDataSource.searchSeries(query = query, page = page)?.let {
            it.results?.let { series ->
                ContentMapper.fromSerieEntityListToModel(series, fetchConfiguration())
            }
        }

    override suspend fun fetchSerieDetails(serieId: Int): SerieDetails? =
        apiDataSource.fetchSerieDetails(serieId)?.let {
            ContentMapper.fromSerieDetailsEntityToModel(it, fetchConfiguration())
        }

    override suspend fun insertSerie(
        serie: Serie,
        seenByUser: Boolean,
        network: Network,
        userPunctuation: Int,
        userComments: String
    ): Boolean =
        userRepository.fetchUser()?.selectedUserGroup?.let {
            firebaseDataSource.insertSerie(
                UserGroupMapper.fromModelToEntity(it),
                serie
            )
        } ?: false
}
