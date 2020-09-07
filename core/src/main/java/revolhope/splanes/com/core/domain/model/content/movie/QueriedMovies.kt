package revolhope.splanes.com.core.domain.model.content.movie

import revolhope.splanes.com.core.domain.model.content.QueriedContent
import java.io.Serializable

data class QueriedMovies(
    override val page: Int,
    override val results: List<Movie>,
    override val totalPages: Int,
    override val totalResults: Int
): QueriedContent(), Serializable