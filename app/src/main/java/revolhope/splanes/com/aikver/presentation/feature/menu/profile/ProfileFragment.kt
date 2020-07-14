package revolhope.splanes.com.aikver.presentation.feature.menu.profile

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.viewmodel.ext.android.viewModel
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.framework.app.observe
import revolhope.splanes.com.aikver.presentation.common.base.BaseActivity
import revolhope.splanes.com.aikver.presentation.common.base.BaseFragment
import revolhope.splanes.com.aikver.presentation.common.invisible
import revolhope.splanes.com.aikver.presentation.common.loadAvatar
import revolhope.splanes.com.aikver.presentation.common.loadCircular
import revolhope.splanes.com.aikver.presentation.common.popupError
import revolhope.splanes.com.aikver.presentation.common.visibility
import revolhope.splanes.com.aikver.presentation.common.visible
import revolhope.splanes.com.aikver.presentation.feature.menu.profile.addmember.AddMemberDialog
import revolhope.splanes.com.aikver.presentation.feature.menu.profile.avatar.UserAvatarActivity
import revolhope.splanes.com.aikver.presentation.feature.menu.profile.managegroup.ManageGroupsActivity
import revolhope.splanes.com.aikver.presentation.feature.menu.profile.managegroup.add.AddGroupDialog
import revolhope.splanes.com.core.domain.model.user.User

class ProfileFragment : BaseFragment(), View.OnClickListener {

    private val viewModel: ProfileViewModel by viewModel()

    private var user: User? = null

    override fun initViews() {
        super.initViews()
        profileAvatarImageView.setOnClickListener(this)
        adminGroupsButton.setOnClickListener(this)
        createGroupButton.setOnClickListener(this)
    }

    override fun initObservers() {
        super.initObservers()
        observe(viewModel.loaderState) {
            if (it) showLoader()
            else hideLoader()
        }
        observe(viewModel.user) {
            user = it
            bindViews(it)
        }
        observe(viewModel.addMemberResult) { result ->
            if (result) {
                viewModel.fetchUser()
            } else if (context != null) {
                popupError(context!!, childFragmentManager)
            }
        }
    }

    private fun bindViews(user: User?) {
        if (user == null) {
            if (context != null) popupError(context!!, childFragmentManager)
        } else {
            bindUserCardView(user)
            bindSelectedUserGroup(user)
        }
    }

    private fun bindUserCardView(user: User) {
        profileAvatarImageView.loadAvatar(user.avatar)
        profileUserNameTextView.text = user.username
    }

    private fun bindSelectedUserGroup(user: User) {
        val group = user.selectedUserGroup
        selectedGroupLayout.visibility(group != null)
        selectedGroupEmptyStateLayout.visibility(group == null)

        if (group != null) {
            selectedGroupImageView.loadCircular(group.icon)
            selectedGroupNameTextView.text = group.name
            if (group.userGroupAdmin.userId == user.id) {
                selectedGroupAddMemberButton.visible()
                selectedGroupAddMemberButton.setOnClickListener { onAddMemberClick() }
            } else {
                selectedGroupAddMemberButton.invisible()
            }

            selectedGroupMembersRecyclerView.layoutManager = LinearLayoutManager(context)
            selectedGroupMembersRecyclerView.adapter =
                UserGroupMembersAdapter(user.id, group.members)
        }
    }

    override fun loadData() {
        super.loadData()
        viewModel.fetchUser()
    }

    private fun onAddMemberClick() =
        AddMemberDialog(::onAddMemberDialogResponse)
            .show(childFragmentManager, AddMemberDialog::class.java.name)

    private fun onAddMemberDialogResponse(username: String) =
        viewModel.addMember(username, user?.selectedUserGroup)

    private fun onAddGroup(groupName: String) = viewModel.addGroup(groupName)

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.profileAvatarImageView -> UserAvatarActivity.start(activity as BaseActivity?)
            R.id.adminGroupsButton -> ManageGroupsActivity.start(activity as BaseActivity?)
            R.id.createGroupButton -> AddGroupDialog(::onAddGroup).show(childFragmentManager)
        }
    }

    override fun getLayoutResource(): Int = R.layout.fragment_profile
}