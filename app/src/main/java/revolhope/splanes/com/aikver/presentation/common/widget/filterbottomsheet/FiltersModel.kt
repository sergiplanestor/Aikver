package revolhope.splanes.com.aikver.presentation.common.widget.filterbottomsheet

import revolhope.splanes.com.aikver.domain.Platform
import java.io.Serializable

data class FiltersModel(
    val input: String? = null,
    val category: String? = null,
    val score: Int? = null,
    val platform: Platform? = null,
    val orderBy: OrderBy = OrderBy.DESCENDING
): Serializable {

    enum class OrderBy {
        ASCENDING,
        DESCENDING
    }
}