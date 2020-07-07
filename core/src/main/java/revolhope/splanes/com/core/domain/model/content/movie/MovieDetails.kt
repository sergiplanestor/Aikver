package revolhope.splanes.com.core.domain.model.content.movie

import revolhope.splanes.com.core.domain.model.content.ContentCollection
import revolhope.splanes.com.core.domain.model.content.ContentDetails
import revolhope.splanes.com.core.domain.model.content.ContentGenres
import revolhope.splanes.com.core.domain.model.content.ContentLanguage
import revolhope.splanes.com.core.domain.model.content.ContentProductionCompany
import revolhope.splanes.com.core.domain.model.content.ContentProductionCountry
import revolhope.splanes.com.core.domain.model.content.ContentStatus
import java.io.Serializable

data class MovieDetails(
    override val backdrop: String,
    val isAdult: Boolean,
    val collection: ContentCollection?,
    val budget: Int,
    override val genres: List<ContentGenres>,
    override val homepage: String,
    override val id: Int,
    val idIMBD: String,
    override val originalLanguage: String,
    override val originalTitle: String,
    override val overview: String,
    override val popularity: Float,
    override val thumbnail: String,
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    val languages: List<ContentLanguage>,
    override val status: ContentStatus,
    val tagLine: String,
    override val title: String,
    val haveVideo: Boolean,
    val productionCompanies: List<ContentProductionCompany>,
    val productionCountries: List<ContentProductionCountry>,
    override val voteAverage: Float,
    override val voteCount: Int
) : ContentDetails(), Serializable