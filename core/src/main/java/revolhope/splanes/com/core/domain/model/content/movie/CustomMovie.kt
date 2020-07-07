package revolhope.splanes.com.core.domain.model.content.movie

import revolhope.splanes.com.core.domain.model.content.Network
import revolhope.splanes.com.core.domain.model.content.movie.Movie
import revolhope.splanes.com.core.domain.model.user.UserGroupMember

class CustomMovie(
    val movie: Movie,
    val userAdded: UserGroupMember,
    val dateAdded: Long,
    val seenBy: List<UserGroupMember>?,
    val network: Network,
    val punctuation: List<Pair<UserGroupMember, Float>>?,
    val comments: List<Pair<UserGroupMember, String>>?
)