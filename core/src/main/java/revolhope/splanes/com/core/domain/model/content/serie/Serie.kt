package revolhope.splanes.com.core.domain.model.content.serie

import revolhope.splanes.com.core.domain.model.content.Content
import java.io.Serializable

data class Serie(
    override val thumbnail: String,
    override val popularity: Float,
    override val id: Int,
    override val backdrop: String,
    override val voteAverage: Float,
    override val overview: String,
    override val genreIds: List<Int>,
    override val originalLanguage: String,
    override val voteCount: Int,
    override val title: String,
    override val originalTitle: String,
    val firstAirDate: String,
    val originalCountries: List<String>
) : Content(), Serializable