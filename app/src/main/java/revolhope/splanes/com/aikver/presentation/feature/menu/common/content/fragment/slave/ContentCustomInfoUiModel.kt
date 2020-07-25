package revolhope.splanes.com.aikver.presentation.feature.menu.common.content.fragment.slave

import revolhope.splanes.com.core.domain.model.content.ContentDetails
import revolhope.splanes.com.core.domain.model.content.Network
import revolhope.splanes.com.core.domain.model.user.UserGroupMember

data class ContentCustomInfoUiModel(
    val content: ContentDetails,
    val haveSeen: Boolean = false,
    val score: Int = -1,
    val network: Network = Network.UNKNOWN,
    val recommendedTo: List<UserGroupMember> = emptyList(),
    val comments: String = ""
)