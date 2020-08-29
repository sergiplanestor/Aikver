package revolhope.splanes.com.aikver.presentation.feature.menu.profile.avatar.typeselector

import revolhope.splanes.com.core.domain.model.user.UserAvatar

data class TypeSelectorUiModel(
    val title: String,
    val avatar: UserAvatar,
    var isChecked: Boolean = false
)