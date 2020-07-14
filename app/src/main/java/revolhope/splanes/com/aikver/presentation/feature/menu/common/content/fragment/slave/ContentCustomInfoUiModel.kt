package revolhope.splanes.com.aikver.presentation.feature.menu.common.content.fragment.slave

import revolhope.splanes.com.core.domain.model.content.Content
import revolhope.splanes.com.core.domain.model.content.Network
import revolhope.splanes.com.core.domain.model.user.UserGroupMember

data class ContentCustomInfoUiModel(
    val content: Content,
    val haveSeen: Boolean = false,
    val score: Int = -1,
    val network: Network = Network.UNKNOWN,
    val recommendedTo: List<UserGroupMember> = emptyList(),
    val comments: String = ""
)