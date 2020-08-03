package revolhope.splanes.com.core.domain.model.content

import revolhope.splanes.com.core.domain.model.user.UserGroupMember
import java.io.Serializable

abstract class CustomContent<T: ContentDetails> : Serializable {
    abstract val content: T
    abstract val userAdded: UserGroupMember
    abstract val dateAdded: Long
    abstract val seenBy: List<UserGroupMember>
    abstract val network: Network
    abstract val recommendedTo: List<UserGroupMember>
    abstract val punctuation: List<Pair<UserGroupMember, Float>>
    abstract val comments: List<Pair<UserGroupMember, String>>
}