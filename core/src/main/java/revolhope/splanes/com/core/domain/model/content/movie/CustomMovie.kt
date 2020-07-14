package revolhope.splanes.com.core.domain.model.content.movie

import revolhope.splanes.com.core.domain.model.content.CustomContent
import revolhope.splanes.com.core.domain.model.content.Network
import revolhope.splanes.com.core.domain.model.user.UserGroupMember

class CustomMovie(
    override val content: Movie,
    override val userAdded: UserGroupMember,
    override val dateAdded: Long,
    override val seenBy: List<UserGroupMember>,
    override val network: Network,
    override val recommendedTo: List<UserGroupMember>,
    override val punctuation: List<Pair<UserGroupMember, Float>>,
    override val comments: List<Pair<UserGroupMember, String>>
) : CustomContent<Movie>()