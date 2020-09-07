package revolhope.splanes.com.aikver.presentation.feature.menu.common.customcontent.widget

import revolhope.splanes.com.core.domain.model.user.UserAvatar

data class CustomContentUserUiModel(
    val userId: String,
    val avatar: UserAvatar,
    val userName: String,
    val punctuation: Float? = null
)