package revolhope.splanes.com.aikver.presentation.common.widget.membergrouppicker.bottomsheet

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_member_group_picker_bottom_sheet.doneButton
import kotlinx.android.synthetic.main.fragment_member_group_picker_bottom_sheet.groupImageView
import kotlinx.android.synthetic.main.fragment_member_group_picker_bottom_sheet.groupMemberCountTextView
import kotlinx.android.synthetic.main.fragment_member_group_picker_bottom_sheet.groupTitleTextView
import kotlinx.android.synthetic.main.fragment_member_group_picker_bottom_sheet.membersRecyclerView
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.presentation.common.base.BaseBottomSheet
import revolhope.splanes.com.aikver.presentation.common.loadGroupIcon
import revolhope.splanes.com.core.domain.model.user.UserGroup
import revolhope.splanes.com.core.domain.model.user.UserGroupMember

class MemberGroupPickerBottomSheet(
    private val group: UserGroup,
    private val usersPicked: List<UserGroupMember>,
    private val onUsersAdded: (List<UserGroupMember>) -> Unit,
    onDismiss: (() -> Unit)? = null
) : BaseBottomSheet(onDismiss) {

    override fun bindView(view: View) {
        groupImageView.loadGroupIcon(group.name, group.userGroupAdmin.avatar.color)
        groupTitleTextView.text = group.name
        groupMemberCountTextView.text = context?.getString(R.string.member_num, group.members.size)
        setUpAdapter(usersPicked)
        doneButton.setOnClickListener {
            onUsersAdded.invoke(
                (membersRecyclerView.adapter as? MemberGroupPickerBottomSheetAdapter)?.pickedMembers
                    ?: emptyList()
            )
            dismiss()
        }
    }

    private fun setUpAdapter(usersPicked: List<UserGroupMember>) {
        membersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        membersRecyclerView.adapter = MemberGroupPickerBottomSheetAdapter(
            group.members.map {
                MemberGroupPickerBottomSheetUiModel(
                    member = it,
                    isPicked = usersPicked.any { picked -> it.userId == picked.userId }
                )
            }
        )
    }

    override fun getLayoutResource(): Int = R.layout.fragment_member_group_picker_bottom_sheet
}