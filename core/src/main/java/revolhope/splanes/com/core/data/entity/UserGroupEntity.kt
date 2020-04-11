package revolhope.splanes.com.core.data.entity

data class UserGroupEntity(
    val id: String?,
    val icon: String?,
    val name: String?,
    val members: MutableList<String>?,
    val userAdmin: String?,
    val dateCreation: Long?
)