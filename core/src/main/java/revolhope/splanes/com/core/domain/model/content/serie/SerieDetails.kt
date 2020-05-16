package revolhope.splanes.com.core.domain.model.content.serie

import revolhope.splanes.com.core.domain.model.content.ContentCreator
import revolhope.splanes.com.core.domain.model.content.ContentGenres
import revolhope.splanes.com.core.domain.model.content.ContentNetwork


data class SerieDetails(
    val backdrop: String,
    val createdBy: List<ContentCreator>,
    val episodeRuntime: List<Int>,
    val firstAirDate: String,
    val genres: List<ContentGenres>,
    val homepage: String,
    val id: Int,
    val isInProduction: Boolean,
    val languages: List<String>,
    val lastAirDate: String,
    val lastEpisodeToAir: Episode,
    val name: String,
    val network: List<ContentNetwork>,
    val numEpisodes: Int,
    val numSeasons: Int,
    val originCountry: List<String>,
    val originalLanguage: String,
    val originalName: String,
    val overview: String,
    val popularity: Float,
    val thumbnail: String,
    val seasons: List<Season>,
    val status: SerieStatus,
    val type: String,
    val voteAverage: Float,
    val voteCount: Int
)