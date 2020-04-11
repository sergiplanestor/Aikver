package revolhope.splanes.com.aikver.presentation.feature.menu.profile

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.viewmodel.ext.android.viewModel
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.framework.app.observe
import revolhope.splanes.com.aikver.presentation.common.base.BaseActivity
import revolhope.splanes.com.aikver.presentation.common.base.BaseFragment
import revolhope.splanes.com.aikver.presentation.common.loadAvatar
import revolhope.splanes.com.aikver.presentation.common.loadCircular
import revolhope.splanes.com.aikver.presentation.common.visibility
import revolhope.splanes.com.aikver.presentation.common.widget.popup.Popup
import revolhope.splanes.com.aikver.presentation.feature.menu.profile.avatar.UserAvatarActivity
import revolhope.splanes.com.core.domain.model.User

class ProfileFragment : BaseFragment(), View.OnClickListener {

    private val viewModel: ProfileViewModel by viewModel()

    override fun initViews() {
        super.initViews()
        profileAvatarImageView.setOnClickListener(this)
    }

    override fun initObservers() {
        super.initObservers()
        observe(viewModel.user, ::bindViews)
    }

    private fun bindViews(user: User?) {
        if (user == null) {
            if (context != null) Popup.showError(context!!, childFragmentManager)
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
            selectedGroupMembersRecyclerView.layoutManager = LinearLayoutManager(context)
            selectedGroupMembersRecyclerView.adapter =
                UserGroupMembersAdapter(user.id, group.members)
        }
    }

    override fun loadData() {
        super.loadData()
        viewModel.fetchUser()
    }

    override fun onClick(view: View?) {
        (activity as BaseActivity?)?.navigateUp(UserAvatarActivity::class.java)
    }

    override fun getLayoutResource(): Int = R.layout.fragment_profile
}