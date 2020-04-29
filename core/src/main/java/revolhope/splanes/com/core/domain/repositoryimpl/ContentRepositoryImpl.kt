package revolhope.splanes.com.core.domain.repositoryimpl


import revolhope.splanes.com.core.data.datasource.ApiDataSource
import revolhope.splanes.com.core.data.repository.ContentRepository

class ContentRepositoryImpl(private val apiDataSource: ApiDataSource) : ContentRepository {

    override suspend fun searchMovies(query: String) {

    }

    override suspend fun searchSeries(query: String) {

    }
}
