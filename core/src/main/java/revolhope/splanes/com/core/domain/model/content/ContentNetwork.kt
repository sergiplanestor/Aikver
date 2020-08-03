package revolhope.splanes.com.core.domain.model.content

import java.io.Serializable

data class ContentNetwork(
    val name: String,
    val id: Int,
    val logo: String,
    val originCountry: String
) : Serializable