package revolhope.splanes.com.core.domain.mapper

import revolhope.splanes.com.core.data.entity.api.content.MovieEntity
import revolhope.splanes.com.core.data.entity.api.content.SerieEntity
import revolhope.splanes.com.core.domain.model.config.Configuration
import revolhope.splanes.com.core.domain.model.config.ImageConfiguration
import revolhope.splanes.com.core.domain.model.content.Movie
import revolhope.splanes.com.core.domain.model.content.Serie

object ContentMapper {

    private const val IMG_POSTER = 0
    private const val IMG_BACKDROP = 1


    fun fromMovieEntityListToModel(
        entities: List<MovieEntity>,
        config: Configuration?
    ): List<Movie> =
        entities.map { fromMovieEntityToModel(it, config) }

    private fun fromMovieEntityToModel(entity: MovieEntity, config: Configuration?): Movie =
        Movie(
            thumbnail = getImagePath(
                IMG_POSTER,
                entity.thumbnail,
                config?.imageConfigurationEntity
            ),
            popularity = entity.popularity ?: Float.NaN,
            id = entity.id ?: -1,
            backdrop = getImagePath(
                IMG_BACKDROP,
                entity.backdrop,
                config?.imageConfigurationEntity
            ),
            voteAverage = entity.voteAverage ?: Float.NaN,
            overview = entity.overview ?: "",
            genreIds = entity.genreIds ?: listOf(),
            originalLanguage = entity.originalLanguage ?: "",
            voteCount = entity.voteCount ?: -1,
            releaseDate = entity.releaseDate ?: "",
            title = entity.title ?: "",
            originalTitle = entity.originalTitle ?: "",
            adult = entity.adult ?: false,
            hasVideo = entity.hasVideo ?: false
        )

    fun fromSerieEntityListToModel(
        entities: List<SerieEntity>,
        config: Configuration?
    ): List<Serie> =
        entities.map { fromSerieEntityToModel(it, config) }

    private fun fromSerieEntityToModel(entity: SerieEntity, config: Configuration?): Serie =
        Serie(
            thumbnail = getImagePath(
                IMG_POSTER,
                entity.thumbnail,
                config?.imageConfigurationEntity
            ),
            popularity = entity.popularity ?: Float.NaN,
            id = entity.id ?: -1,
            backdrop = getImagePath(
                IMG_BACKDROP,
                entity.backdrop,
                config?.imageConfigurationEntity
            ),
            voteAverage = entity.voteAverage ?: Float.NaN,
            overview = entity.overview ?: "",
            genreIds = entity.genreIds ?: listOf(),
            originalLanguage = entity.originalLanguage ?: "",
            voteCount = entity.voteCount ?: -1,
            title = entity.name ?: "",
            originalTitle = entity.originalName ?: "",
            firstAirDate = entity.firstAirDate ?: "",
            originalCountries = entity.originalCountries ?: listOf()
        )

    private fun getImagePath(
        type: Int,
        lastPath: String?,
        config: ImageConfiguration?
    ): String =
        if (lastPath.isNullOrBlank() || config == null) {
            ""
        } else {
            "${config.secureBaseUrl}${
            if (type == IMG_POSTER) {
                config.posterSizes.last { it.contains("w") }
            } else {
                config.backdropSizes.last { it.contains("w") }
            }
            }$lastPath"
        }

}