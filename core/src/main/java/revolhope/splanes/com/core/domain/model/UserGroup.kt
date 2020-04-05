package revolhope.splanes.com.core.domain.model

data class UserGroup(
    val id: String,
    val name: String,
    val members: MutableList<String> = mutableListOf(),
    val userCreator: String,
    val dateCreation: Long = System.currentTimeMillis()
)