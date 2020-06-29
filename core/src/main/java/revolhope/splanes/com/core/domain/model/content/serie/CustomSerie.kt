package revolhope.splanes.com.core.domain.model.content.serie

import revolhope.splanes.com.core.domain.model.content.Network
import revolhope.splanes.com.core.domain.model.user.UserGroupMember

data class CustomSerie(
    val serie: Serie,
    val userAdded: UserGroupMember,
    val dateAdded: Long,
    val seenBy: List<UserGroupMember>?,
    val network: Network,
    val punctuation: List<Pair<UserGroupMember, Float>>?,
    val comments: List<Pair<UserGroupMember, String>>?
)