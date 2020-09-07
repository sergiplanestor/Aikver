package revolhope.splanes.com.core.data.entity.user

data class UserGroupEntity(
    val id: String?,
    val name: String?,
    val members: MutableList<String>?,
    val userAdmin: String?,
    val dateCreation: Long?
)