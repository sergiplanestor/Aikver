package revolhope.splanes.com.core.domain.model.content

import java.io.Serializable

data class Movie(
    override val thumbnail: String,
    override val popularity: Float,
    override val id: Int,
    override val backdrop: String,
    override val voteAverage: Float,
    override val overview: String,
    override val genreIds: List<Int>,
    override val originalLanguage: String,
    override val voteCount: Int,
    override val originalTitle: String,
    override val title: String,
    val adult: Boolean,
    val releaseDate: String,
    val hasVideo: Boolean
) : Content(), Serializable