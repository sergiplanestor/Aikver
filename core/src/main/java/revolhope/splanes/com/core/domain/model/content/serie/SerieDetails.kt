package revolhope.splanes.com.core.domain.model.content.serie

import revolhope.splanes.com.core.domain.model.content.ContentCreator
import revolhope.splanes.com.core.domain.model.content.ContentDetails
import revolhope.splanes.com.core.domain.model.content.ContentGenres
import revolhope.splanes.com.core.domain.model.content.ContentNetwork
import revolhope.splanes.com.core.domain.model.content.ContentStatus
import java.io.Serializable


data class SerieDetails(
    override val backdrop: String,
    val createdBy: List<ContentCreator>,
    val episodeRuntime: List<Int>,
    val firstAirDate: String,
    override val genres: List<ContentGenres>,
    override val homepage: String,
    override val id: Int,
    val isInProduction: Boolean,
    val languages: List<String>,
    val lastAirDate: String,
    val lastEpisodeToAir: Episode,
    override val title: String,
    val network: List<ContentNetwork>,
    val numEpisodes: Int,
    val numSeasons: Int,
    val originCountry: List<String>,
    override val originalLanguage: String,
    override val originalTitle: String,
    override val overview: String,
    override val popularity: Float,
    override val thumbnail: String,
    val seasons: List<Season>,
    override val status: ContentStatus,
    val type: String,
    override val voteAverage: Float,
    override val voteCount: Int
) : ContentDetails(), Serializable {

    companion object {
        val empty: SerieDetails
            get() =
                SerieDetails(
                    backdrop = "",
                    createdBy = emptyList(),
                    episodeRuntime = emptyList(),
                    firstAirDate = "",
                    genres = emptyList(),
                    homepage = "",
                    id = Int.MIN_VALUE,
                    isInProduction = false,
                    languages = emptyList(),
                    lastAirDate = "",
                    lastEpisodeToAir = Episode.empty,
                    title = "",
                    network = emptyList(),
                    numEpisodes = Int.MIN_VALUE,
                    numSeasons = Int.MIN_VALUE,
                    originCountry = emptyList(),
                    originalLanguage = "",
                    originalTitle = "",
                    overview = "",
                    popularity = Float.NaN,
                    thumbnail = "",
                    seasons = emptyList(),
                    status = ContentStatus.UNKNOWN,
                    type = "",
                    voteAverage = Float.NaN,
                    voteCount = Int.MIN_VALUE
                )
    }

}