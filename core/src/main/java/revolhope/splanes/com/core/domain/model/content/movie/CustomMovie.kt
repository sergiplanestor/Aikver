package revolhope.splanes.com.core.domain.model.content.movie

import revolhope.splanes.com.core.domain.model.content.CustomContent
import revolhope.splanes.com.core.domain.model.content.Network
import revolhope.splanes.com.core.domain.model.user.UserGroupMember
import java.io.Serializable

class CustomMovie(
    override val content: MovieDetails,
    override val userAdded: UserGroupMember,
    override val dateAdded: Long,
    override val seenBy: MutableList<UserGroupMember>,
    override val network: Network,
    override val recommendedTo: List<UserGroupMember>,
    override val punctuation: MutableList<Pair<UserGroupMember, Float>>,
    override val comments: MutableList<Pair<UserGroupMember, String>>
) : CustomContent<MovieDetails>(), Serializable