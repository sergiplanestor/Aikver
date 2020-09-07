package revolhope.splanes.com.aikver.presentation.common.widget.membergrouppicker.bottomsheet

import revolhope.splanes.com.core.domain.model.user.UserGroupMember

data class MemberGroupPickerBottomSheetUiModel(
    val member: UserGroupMember,
    var isPicked: Boolean = false
)