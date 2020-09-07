package revolhope.splanes.com.core.data.datasource

import revolhope.splanes.com.core.domain.model.content.ContentDetails
import revolhope.splanes.com.core.domain.model.content.CustomContent
import revolhope.splanes.com.core.domain.model.content.movie.QueriedMovies
import revolhope.splanes.com.core.domain.model.content.serie.QueriedSeries

object CacheContentDataSource {

    private var popularSeriesCache: QueriedSeries? = null
    private var popularMoviesCache: QueriedMovies? = null
    private var popularGroupContentCache: List<CustomContent<ContentDetails>>? = null

    fun fetchPopularSeries() = popularSeriesCache

    fun fetchPopularMovies() = popularMoviesCache

    fun fetchGroupContent() = popularGroupContentCache

    fun insertPopularSeries(data: QueriedSeries?) {
        popularSeriesCache = data
    }

    fun insertPopularMovies(data: QueriedMovies?) {
        popularMoviesCache = data
    }

    fun insertGroupContent(data: List<CustomContent<ContentDetails>>?) {
        popularGroupContentCache = data
    }

    fun clear() {
        popularSeriesCache = null
        popularMoviesCache = null
        popularGroupContentCache = null
    }

}