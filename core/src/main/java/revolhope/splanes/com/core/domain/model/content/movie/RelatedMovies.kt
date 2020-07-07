package revolhope.splanes.com.core.domain.model.content.movie

import revolhope.splanes.com.core.domain.model.content.RelatedContent
import java.io.Serializable

data class RelatedMovies(
    override val page: Int,
    override val results: List<Movie>,
    override val totalPages: Int,
    override val totalResults: Int
): RelatedContent(), Serializable