package revolhope.splanes.com.core.domain.model.content

import java.io.Serializable

abstract class QueriedContent : Serializable {
    abstract val page: Int
    abstract val results: List<Content>
    abstract val totalPages: Int
    abstract val totalResults: Int
}