package revolhope.splanes.com.core.domain.model.content

import java.io.Serializable

data class ContentCollection(
    val name: String,
    val id: Int,
    val logo: String,
    val backdrop: String
) : Serializable