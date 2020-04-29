package revolhope.splanes.com.core.domain.mapper

import revolhope.splanes.com.core.data.entity.content.movie.MovieEntity
import revolhope.splanes.com.core.data.entity.content.serie.SerieEntity
import revolhope.splanes.com.core.domain.model.Movie
import revolhope.splanes.com.core.domain.model.Serie

object ContentMapper {

    fun fromMovieEntityListToModel(entities: List<MovieEntity>): List<Movie> =
        entities.map(::fromMovieEntityToModel)

    private fun fromMovieEntityToModel(entity: MovieEntity): Movie =
        Movie(
            thumbnail = entity.thumbnail ?: "",
            popularity = entity.popularity ?: Float.NaN,
            id = entity.id ?: -1,
            backdrop = entity.backdrop ?: "",
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

    fun fromSerieEntityListToModel(entities: List<SerieEntity>): List<Serie> =
        entities.map(::fromSerieEntityToModel)

    private fun fromSerieEntityToModel(entity: SerieEntity): Serie =
        Serie(
            thumbnail = entity.thumbnail ?: "",
            popularity = entity.popularity ?: Float.NaN,
            id = entity.id ?: -1,
            backdrop = entity.backdrop ?: "",
            voteAverage = entity.voteAverage ?: Float.NaN,
            overview = entity.overview ?: "",
            genreIds = entity.genreIds ?: listOf(),
            originalLanguage = entity.originalLanguage ?: "",
            voteCount = entity.voteCount ?: -1,
            name = entity.name ?: "",
            originalName = entity.originalName ?: "",
            firstAirDate = entity.firstAirDate ?: "",
            originalCountries = entity.originalCountries ?: listOf()
        )
}