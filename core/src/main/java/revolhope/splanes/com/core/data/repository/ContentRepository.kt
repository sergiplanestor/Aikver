package revolhope.splanes.com.core.data.repository

import revolhope.splanes.com.core.domain.model.config.Configuration
import revolhope.splanes.com.core.domain.model.content.Network
import revolhope.splanes.com.core.domain.model.content.movie.Movie
import revolhope.splanes.com.core.domain.model.content.movie.MovieDetails
import revolhope.splanes.com.core.domain.model.content.movie.QueriedMovies
import revolhope.splanes.com.core.domain.model.content.serie.QueriedSeries
import revolhope.splanes.com.core.domain.model.content.serie.Serie
import revolhope.splanes.com.core.domain.model.content.serie.SerieDetails
import revolhope.splanes.com.core.domain.model.user.UserGroupMember

interface ContentRepository {

    suspend fun fetchConfiguration(): Configuration?

    suspend fun searchMovies(query: String): List<Movie>?

    suspend fun searchSeries(query: String, page: Int = 1): List<Serie>?

    suspend fun fetchSerieDetails(serieId: Int): SerieDetails?

    suspend fun fetchMovieDetails(movieId: Int): MovieDetails?

    suspend fun fetchRelatedSeries(serieId: Int, page: Int): QueriedSeries?

    suspend fun fetchRelatedMovies(movieId: Int, page: Int): QueriedMovies?

    suspend fun fetchPopularSeries(page: Int): QueriedSeries?

    suspend fun fetchPopularMovies(page: Int): QueriedMovies?

    suspend fun insertSerie(
        serie: Serie,
        seenByUser: Boolean,
        network: Network,
        recommendedTo: List<UserGroupMember>,
        userPunctuation: Int,
        userComments: String
    ): Boolean

    suspend fun insertMovie(
        movie: Movie,
        seenByUser: Boolean,
        network: Network,
        recommendedTo: List<UserGroupMember>,
        userPunctuation: Int,
        userComments: String
    ): Boolean
}