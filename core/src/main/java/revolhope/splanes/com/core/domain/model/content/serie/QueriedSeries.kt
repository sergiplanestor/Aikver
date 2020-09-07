package revolhope.splanes.com.core.domain.model.content.serie

import revolhope.splanes.com.core.domain.model.content.QueriedContent
import java.io.Serializable

data class QueriedSeries(
    override val page: Int,
    override val results: List<Serie>,
    override val totalPages: Int,
    override val totalResults: Int
): QueriedContent(), Serializable