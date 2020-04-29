package revolhope.splanes.com.core.data.datasource

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import revolhope.splanes.com.core.data.entity.content.SearchContentEntity
import revolhope.splanes.com.core.data.entity.content.movie.MovieEntity
import revolhope.splanes.com.core.data.entity.content.serie.SerieEntity
import revolhope.splanes.com.core.data.entity.user.UserAvatarTypesEntity

interface ApiDataSource {

    // Avatar api
    @GET
    suspend fun fetchAvatarTypes(@Url url: String): UserAvatarTypesEntity?

    // Serie & movie api
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("page") page: Int = 1,
        @Query("query") query: String,
        @Query("include_adult") showAdult: Boolean = false,
        @Query("primary_release_year") releaseDateYear: Int? = null
    ): SearchContentEntity<MovieEntity>?

    @GET("search/tv")
    suspend fun searchSeries(
        @Query("page") page: Int = 1,
        @Query("query") query: String,
        @Query("include_adult") showAdult: Boolean = false,
        @Query("first_air_date_year") firstAirDateYear: Int? = null
    ): SearchContentEntity<SerieEntity>?
}