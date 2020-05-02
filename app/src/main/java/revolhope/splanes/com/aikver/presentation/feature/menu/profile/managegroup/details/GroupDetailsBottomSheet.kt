package revolhope.splanes.com.aikver.presentation.feature.menu.profile.managegroup.details

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_group_details_bottom_sheet.addMemberButton
import kotlinx.android.synthetic.main.fragment_group_details_bottom_sheet.deleteGroupButton
import kotlinx.android.synthetic.main.fragment_group_details_bottom_sheet.footerViews
import kotlinx.android.synthetic.main.fragment_group_details_bottom_sheet.groupImageView
import kotlinx.android.synthetic.main.fragment_group_details_bottom_sheet.groupMemberCountTextView
import kotlinx.android.synthetic.main.fragment_group_details_bottom_sheet.groupTitleTextView
import kotlinx.android.synthetic.main.fragment_group_details_bottom_sheet.membersRecyclerView
import org.koin.android.ext.android.inject
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.framework.app.observe
import revolhope.splanes.com.aikver.presentation.common.base.BaseBottomSheet
import revolhope.splanes.com.aikver.presentation.common.loadGroupIcon
import revolhope.splanes.com.aikver.presentation.common.popup
import revolhope.splanes.com.aikver.presentation.common.popupError
import revolhope.splanes.com.aikver.presentation.common.visibility
import revolhope.splanes.com.aikver.presentation.common.widget.popup.PopupModel
import revolhope.splanes.com.aikver.presentation.feature.menu.profile.addmember.AddMemberDialog
import revolhope.splanes.com.core.domain.model.user.UserGroup
import revolhope.splanes.com.core.domain.model.user.UserGroupMember

class GroupDetailsBottomSheet(
    private val userId: String,
    private val group: UserGroup,
    onDismiss: (() -> Unit)? = null
) : BaseBottomSheet(onDismiss) {

    private val viewModel: GroupDetailsViewModel by inject()

    override fun getLayoutResource(): Int = R.layout.fragment_group_details_bottom_sheet

    override fun bindView(view: View) {
        groupImageView.loadGroupIcon(group.name, group.userGroupAdmin.avatar.color)
        groupTitleTextView.text = group.name
        groupMemberCountTextView.text = context?.getString(R.string.member_num, group.members.size)
        addMemberButton.visibility(show = isAdmin())
        if (isAdmin()) {
            addMemberButton.setOnClickListener { onAddMemberClick() }
            deleteGroupButton.setOnClickListener { onDeleteGroupClick() }
        }
        membersRecyclerView.layoutManager = LinearLayoutManager(context)
        membersRecyclerView.adapter =
            MembersAdapter(group.members, isAdmin(), userId, ::onDeleteClick)
        footerViews.visibility(show = isAdmin())
        initObservers()
    }

    private fun initObservers() {
        observeLoader(viewModel.loaderState)
        observe(viewModel.memberDeletion, ::updateView)
        observe(viewModel.addMemberResult, ::updateView)
        observe(viewModel.groupDeletion) {
            if (it) dismiss()
            else if (context != null) popupError(context!!, childFragmentManager)
        }
    }

    private fun isAdmin(): Boolean = userId == group.userGroupAdmin.userId

    private fun updateView(items: List<UserGroupMember>) {
        (membersRecyclerView.adapter as MembersAdapter).updateItems(items)
        groupMemberCountTextView.text = context?.getString(R.string.member_num, items.size)
    }

    private fun onAddMemberClick() =
        AddMemberDialog(::onAddMemberDialogResponse)
            .show(childFragmentManager, AddMemberDialog::class.java.name)

    private fun onAddMemberDialogResponse(username: String) =
        viewModel.addMember(username, group)

    private fun onDeleteClick(member: UserGroupMember) {
        popup(
            childFragmentManager,
            PopupModel(
                title = getString(R.string.confirmation),
                message = getString(R.string.profile_delete_member_from_group_confirmation),
                buttonPositive = getString(R.string.delete),
                buttonNegative = getString(R.string.cancel),
                buttonPositiveListener = {
                    viewModel.deleteMember(member)
                }
            )
        )
    }

    private fun onDeleteGroupClick() {
        popup(
            childFragmentManager,
            PopupModel(
                title = getString(R.string.confirmation),
                message = getString(R.string.profile_delete_group_confirmation),
                buttonPositive = getString(R.string.delete),
                buttonNegative = getString(R.string.cancel),
                buttonPositiveListener = {
                    viewModel.deleteGroup(group)
                }
            )
        )
    }
}