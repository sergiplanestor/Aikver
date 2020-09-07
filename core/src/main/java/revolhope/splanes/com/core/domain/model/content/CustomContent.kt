package revolhope.splanes.com.core.domain.model.content

import revolhope.splanes.com.core.domain.model.content.movie.CustomMovie
import revolhope.splanes.com.core.domain.model.content.serie.CustomSerie
import revolhope.splanes.com.core.domain.model.content.serie.SerieDetails
import revolhope.splanes.com.core.domain.model.user.UserGroupMember
import java.io.Serializable

abstract class CustomContent<T: ContentDetails> : Serializable {
    abstract val content: T
    abstract val userAdded: UserGroupMember
    abstract val dateAdded: Long
    abstract val seenBy: MutableList<UserGroupMember>
    abstract val network: Network
    abstract val recommendedTo: List<UserGroupMember>
    abstract val punctuation: MutableList<Pair<UserGroupMember, Float>>
    abstract val comments: MutableList<Pair<UserGroupMember, String>>

    fun toCustomSerie(): CustomSerie = this as CustomSerie

    fun toCustomMovie(): CustomMovie = this as CustomMovie

}