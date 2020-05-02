package revolhope.splanes.com.core.data.datasource

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url
import revolhope.splanes.com.core.data.entity.api.content.SearchContentEntity
import revolhope.splanes.com.core.data.entity.api.configuration.ConfigurationEntity
import revolhope.splanes.com.core.data.entity.api.content.movie.MovieEntity
import revolhope.splanes.com.core.data.entity.api.content.serie.SerieDetailsEntity
import revolhope.splanes.com.core.data.entity.api.content.serie.SerieEntity
import revolhope.splanes.com.core.data.entity.user.UserAvatarTypesEntity

interface ApiDataSource {

    // Avatar api
    @GET
    suspend fun fetchAvatarTypes(@Url url: String): UserAvatarTypesEntity?

    // Serie & movie api
    @GET("configuration")
    suspend fun fetchConfiguration(): ConfigurationEntity?

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

    @GET("tv/{id}")
    suspend fun fetchSerieDetails(@Path("id") serieId: Int): SerieDetailsEntity?
}