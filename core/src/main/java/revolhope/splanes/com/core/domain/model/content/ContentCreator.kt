package revolhope.splanes.com.core.domain.model.content

import java.io.Serializable

data class ContentCreator(
    val id: Int,
    val creditId: String,
    val name: String,
    val gender: Int,
    val profile: String
) : Serializable