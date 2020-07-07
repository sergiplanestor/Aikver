package revolhope.splanes.com.core.domain.model.content.serie

import revolhope.splanes.com.core.domain.model.content.RelatedContent
import java.io.Serializable

data class RelatedSeries(
    override val page: Int,
    override val results: List<Serie>,
    override val totalPages: Int,
    override val totalResults: Int
): RelatedContent(), Serializable