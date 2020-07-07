package revolhope.splanes.com.core.domain.model.content

import java.io.Serializable

data class ContentProductionCompany(
    val name: String,
    val id: Int,
    val logo: String,
    val originCounty: String
): Serializable