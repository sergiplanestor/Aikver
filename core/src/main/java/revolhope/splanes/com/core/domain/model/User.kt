package revolhope.splanes.com.core.domain.model

import java.io.Serializable

data class User(
    val id: String,
    val username: String,
    val userGroups: MutableList<String> = mutableListOf()
) : Serializable