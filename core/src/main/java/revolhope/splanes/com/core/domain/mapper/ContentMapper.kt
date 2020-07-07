package revolhope.splanes.com.core.domain.mapper

import revolhope.splanes.com.core.data.entity.api.content.ContentCollectionEntity
import revolhope.splanes.com.core.data.entity.api.content.ContentCreatorEntity
import revolhope.splanes.com.core.data.entity.api.content.ContentGenresEntity
import revolhope.splanes.com.core.data.entity.api.content.ContentLanguageEntity
import revolhope.splanes.com.core.data.entity.api.content.ContentNetworkEntity
import revolhope.splanes.com.core.data.entity.api.content.ContentProductionCompanyEntity
import revolhope.splanes.com.core.data.entity.api.content.ContentProductionCountryEntity
import revolhope.splanes.com.core.data.entity.api.content.movie.MovieDetailsEntity
import revolhope.splanes.com.core.data.entity.api.content.movie.MovieEntity
import revolhope.splanes.com.core.data.entity.api.content.movie.RelatedMoviesEntity
import revolhope.splanes.com.core.data.entity.api.content.serie.EpisodeEntity
import revolhope.splanes.com.core.data.entity.api.content.serie.RelatedSeriesEntity
import revolhope.splanes.com.core.data.entity.api.content.serie.SeasonEntity
import revolhope.splanes.com.core.data.entity.api.content.serie.SerieDetailsEntity
import revolhope.splanes.com.core.data.entity.api.content.serie.SerieEntity
import revolhope.splanes.com.core.data.entity.content.CustomMovieEntity
import revolhope.splanes.com.core.data.entity.content.CustomSerieEntity
import revolhope.splanes.com.core.domain.model.config.Configuration
import revolhope.splanes.com.core.domain.model.config.ImageConfiguration
import revolhope.splanes.com.core.domain.model.content.ContentCollection
import revolhope.splanes.com.core.domain.model.content.ContentCreator
import revolhope.splanes.com.core.domain.model.content.ContentGenres
import revolhope.splanes.com.core.domain.model.content.ContentLanguage
import revolhope.splanes.com.core.domain.model.content.ContentNetwork
import revolhope.splanes.com.core.domain.model.content.ContentProductionCompany
import revolhope.splanes.com.core.domain.model.content.ContentProductionCountry
import revolhope.splanes.com.core.domain.model.content.ContentStatus
import revolhope.splanes.com.core.domain.model.content.movie.Movie
import revolhope.splanes.com.core.domain.model.content.movie.MovieDetails
import revolhope.splanes.com.core.domain.model.content.movie.CustomMovie
import revolhope.splanes.com.core.domain.model.content.movie.RelatedMovies
import revolhope.splanes.com.core.domain.model.content.serie.CustomSerie
import revolhope.splanes.com.core.domain.model.content.serie.Episode
import revolhope.splanes.com.core.domain.model.content.serie.RelatedSeries
import revolhope.splanes.com.core.domain.model.content.serie.Season
import revolhope.splanes.com.core.domain.model.content.serie.Serie
import revolhope.splanes.com.core.domain.model.content.serie.SerieDetails

object ContentMapper {

    private enum class ImageType {
        LOGO,
        POSTER,
        BACKDROP,
        STILL
    }

    fun fromMovieEntityListToModel(
        entities: List<MovieEntity>,
        config: Configuration?
    ): List<Movie> =
        entities.map { fromMovieEntityToModel(it, config) }

    private fun fromMovieEntityToModel(entity: MovieEntity, config: Configuration?): Movie =
        Movie(
            thumbnail = getImagePath(
                ImageType.POSTER,
                entity.thumbnail,
                config?.imageConfiguration
            ),
            popularity = entity.popularity ?: Float.NaN,
            id = entity.id ?: -1,
            backdrop = getImagePath(
                ImageType.BACKDROP,
                entity.backdrop,
                config?.imageConfiguration
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
                ImageType.POSTER,
                entity.thumbnail,
                config?.imageConfiguration
            ),
            popularity = entity.popularity ?: Float.NaN,
            id = entity.id ?: -1,
            backdrop = getImagePath(
                ImageType.BACKDROP,
                entity.backdrop,
                config?.imageConfiguration
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

    fun fromSerieDetailsEntityToModel(
        entity: SerieDetailsEntity,
        config: Configuration?
    ): SerieDetails =
        SerieDetails(
            backdrop = getImagePath(
                ImageType.BACKDROP,
                entity.backdrop,
                config?.imageConfiguration
            ),
            createdBy = entity.createdBy?.map(::fromContentCreatorEntityToModel) ?: listOf(),
            episodeRuntime = entity.episodeRuntime ?: listOf(),
            firstAirDate = entity.firstAirDate ?: "",
            genres = entity.genres?.map(::fromContentGenresEntityToModel) ?: listOf(),
            homepage = entity.homepage ?: "",
            id = entity.id ?: -1,
            isInProduction = entity.isInProduction ?: false,
            languages = entity.languages ?: listOf(),
            lastAirDate = entity.lastAirDate ?: "",
            lastEpisodeToAir = entity.lastEpisodeToAir?.let(::fromEpisodeEntityToModel)
                ?: Episode.getEmpty(),
            title = entity.name ?: "",
            network = entity.network?.map { fromContentNetworkEntityToModel(it, config) }
                ?: listOf(),
            numEpisodes = entity.numEpisodes ?: -1,
            numSeasons = entity.numSeasons ?: -1,
            originCountry = entity.originCountry ?: listOf(),
            originalLanguage = entity.originalLanguage ?: "",
            originalTitle = entity.originalName ?: "",
            overview = entity.overview ?: "",
            popularity = entity.popularity ?: Float.NaN,
            thumbnail = getImagePath(
                ImageType.POSTER,
                entity.thumbnail,
                config?.imageConfiguration
            ),
            seasons = entity.seasons?.map { fromSeasonEntityToModel(it, config) } ?: listOf(),
            status = ContentStatus.parse(entity.status),
            type = entity.type ?: "",
            voteAverage = entity.voteAverage ?: Float.NaN,
            voteCount = entity.voteCount ?: -1
        )

    fun fromMovieDetailsEntityToModel(
        entity: MovieDetailsEntity,
        config: Configuration?
    ): MovieDetails =
        MovieDetails(
            backdrop = getImagePath(
                ImageType.BACKDROP,
                entity.backdrop,
                config?.imageConfiguration
            ),
            isAdult = entity.isAdult ?: false,
            collection = entity.collection?.let { fromCollectionEntityToModel(it, config) },
            budget = entity.budget ?: -1,
            genres = entity.genres?.map(::fromContentGenresEntityToModel) ?: listOf(),
            homepage = entity.homepage ?: "",
            idIMBD = entity.idIMBD ?: "",
            id = entity.id ?: -1,
            originalLanguage = entity.originalLanguage ?: "",
            originalTitle = entity.originalTitle ?: "",
            overview = entity.overview ?: "",
            popularity = entity.popularity ?: Float.NaN,
            thumbnail = getImagePath(
                ImageType.POSTER,
                entity.thumbnail,
                config?.imageConfiguration
            ),
            releaseDate = entity.releaseDate ?: "",
            revenue = entity.revenue ?: -1,
            runtime = entity.runtime ?: -1,
            languages = entity.languages?.map(::fromLanguagesEntityToModel) ?: listOf(),
            status = ContentStatus.parse(entity.status),
            tagLine = entity.tagLine ?: "",
            title = entity.title ?: "",
            haveVideo = entity.haveVideo ?: false,
            productionCompanies = entity.productionCompanies?.map {
                fromProductionCompanyEntityToModel(it, config)
            } ?: listOf(),
            productionCountries = entity.productionCountries?.map(::fromProductionCountryEntityToModel)
                ?: listOf(),
            voteAverage = entity.voteAverage ?: Float.NaN,
            voteCount = entity.voteCount ?: -1
        )

    fun fromRelatedMoviesEntityToModel(
        entity: RelatedMoviesEntity,
        config: Configuration?
    ): RelatedMovies =
        RelatedMovies(
            page = entity.page ?: -1,
            results = entity.results?.map { fromMovieEntityToModel(it, config) } ?: emptyList(),
            totalPages = entity.totalPages ?: -1,
            totalResults = entity.totalResults ?: -1
        )

    fun fromRelatedSeriesEntityToModel(
        entity: RelatedSeriesEntity,
        config: Configuration?
    ): RelatedSeries =
        RelatedSeries(
            page = entity.page ?: -1,
            results = entity.results?.map { fromSerieEntityToModel(it, config) } ?: emptyList(),
            totalPages = entity.totalPages ?: -1,
            totalResults = entity.totalResults ?: -1
        )

    private fun fromCollectionEntityToModel(
        entity: ContentCollectionEntity,
        config: Configuration?
    ): ContentCollection =
        ContentCollection(
            name = entity.name ?: "",
            id = entity.id ?: -1,
            logo = getImagePath(
                ImageType.LOGO,
                entity.logo,
                config?.imageConfiguration
            ),
            backdrop = getImagePath(
                ImageType.BACKDROP,
                entity.backdrop,
                config?.imageConfiguration
            )
        )

    private fun fromLanguagesEntityToModel(entity: ContentLanguageEntity): ContentLanguage =
        ContentLanguage(
            iso = entity.iso ?: "",
            name = entity.name ?: ""
        )

    private fun fromProductionCompanyEntityToModel(
        entity: ContentProductionCompanyEntity,
        config: Configuration?
    ): ContentProductionCompany =
        ContentProductionCompany(
            name = entity.name ?: "",
            id = entity.id ?: -1,
            logo = getImagePath(
                ImageType.LOGO,
                entity.logo,
                config?.imageConfiguration
            ),
            originCounty = entity.originCounty ?: ""
        )

    private fun fromProductionCountryEntityToModel(
        entity: ContentProductionCountryEntity
    ): ContentProductionCountry =
        ContentProductionCountry(
            iso = entity.iso ?: "",
            name = entity.name ?: ""
        )

    private fun fromSeasonEntityToModel(
        entity: SeasonEntity,
        config: Configuration?
    ): Season =
        Season(
            airDate = entity.airDate ?: "",
            episodeCount = entity.episodeCount ?: -1,
            id = entity.id ?: -1,
            name = entity.name ?: "",
            overview = entity.overview ?: "",
            thumbnail = getImagePath(
                ImageType.POSTER,
                entity.thumbnail,
                config?.imageConfiguration
            ),
            numSeason = entity.numSeason ?: -1
        )

    private fun fromContentNetworkEntityToModel(
        entity: ContentNetworkEntity,
        config: Configuration?
    ): ContentNetwork =
        ContentNetwork(
            name = entity.name ?: "",
            id = entity.id ?: -1,
            logo = getImagePath(
                ImageType.LOGO,
                entity.logo,
                config?.imageConfiguration
            ),
            originCountry = entity.originCountry ?: ""
        )

    private fun fromEpisodeEntityToModel(entity: EpisodeEntity): Episode =
        Episode(
            airDate = entity.airDate ?: "",
            numSeason = entity.numSeason ?: -1,
            numEpisode = entity.numEpisode ?: -1,
            id = entity.id ?: -1,
            overview = entity.overview ?: "",
            name = entity.name ?: "",
            productionCode = entity.productionCode ?: "",
            showId = entity.showId ?: -1,
            still = entity.still ?: "",
            voteAverage = entity.voteAverage ?: Float.NaN,
            voteCount = entity.voteCount ?: -1
        )

    private fun fromContentGenresEntityToModel(entity: ContentGenresEntity): ContentGenres =
        ContentGenres(id = entity.id ?: -1, name = entity.name ?: "")

    private fun fromContentCreatorEntityToModel(entity: ContentCreatorEntity): ContentCreator =
        ContentCreator(
            id = entity.id ?: -1,
            creditId = entity.creditId ?: "",
            name = entity.name ?: "",
            gender = entity.gender ?: -1,
            profile = entity.profile ?: ""
        )

    private fun getImagePath(
        type: ImageType,
        lastPath: String?,
        config: ImageConfiguration?
    ): String =
        if (lastPath.isNullOrBlank() || config == null) {
            ""
        } else {
            "${config.secureBaseUrl}${
            when (type) {
                ImageType.LOGO -> config.logoSizes.last { it.contains("w") }
                ImageType.POSTER -> config.posterSizes.last { it.contains("w") }
                ImageType.BACKDROP -> config.backdropSizes.last { it.contains("w") }
                ImageType.STILL -> config.stillSizes.last { it.contains("w") }
            }
            }$lastPath"
        }

    fun fromCustomSerieModelToEntity(model: CustomSerie): CustomSerieEntity =
        CustomSerieEntity(
            serieId = model.serie.id.toString(),
            userAdded = model.userAdded.userId,
            dateAdded = model.dateAdded,
            seenBy = model.seenBy?.map { it.userId },
            network = model.network.id,
            punctuation = model.punctuation?.map { it.first.userId to it.second },
            comments = model.comments?.map { it.first.userId to it.second }
        )

    fun fromCustomMovieModelToEntity(model: CustomMovie): CustomMovieEntity =
        CustomMovieEntity(
            movieId = model.movie.id.toString(),
            userAdded = model.userAdded.userId,
            dateAdded = model.dateAdded,
            seenBy = model.seenBy?.map { it.userId },
            network = model.network.id,
            punctuation = model.punctuation?.map { it.first.userId to it.second },
            comments = model.comments?.map { it.first.userId to it.second }
        )
}