package revolhope.splanes.com.aikver.presentation.feature.menu.common.customcontent.widget.comments

import revolhope.splanes.com.core.domain.model.user.UserAvatar

data class CommentUiModel(
    val avatar: UserAvatar,
    val name: String,
    val comment: String,
    val isCurrentUser: Boolean = false
)