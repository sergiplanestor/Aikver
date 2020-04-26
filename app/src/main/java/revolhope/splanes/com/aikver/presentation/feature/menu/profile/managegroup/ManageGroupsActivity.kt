package revolhope.splanes.com.aikver.presentation.feature.menu.profile.managegroup

import android.content.Intent
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_manage_groups.addButtonEmptyState
import kotlinx.android.synthetic.main.activity_manage_groups.emptyState
import kotlinx.android.synthetic.main.activity_manage_groups.groupsRecyclerView
import kotlinx.android.synthetic.main.activity_manage_groups.toolbar
import org.koin.android.viewmodel.ext.android.viewModel
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.framework.app.observe
import revolhope.splanes.com.aikver.presentation.common.base.BaseActivity
import revolhope.splanes.com.aikver.presentation.common.popup
import revolhope.splanes.com.aikver.presentation.common.visibility
import revolhope.splanes.com.aikver.presentation.common.widget.popup.PopupModel
import revolhope.splanes.com.aikver.presentation.feature.menu.profile.managegroup.add.AddGroupDialog
import revolhope.splanes.com.aikver.presentation.feature.menu.profile.managegroup.details.GroupDetailsBottomSheet
import revolhope.splanes.com.core.domain.model.UserGroup

class ManageGroupsActivity : BaseActivity() {

    private val viewModel: ManageGroupsViewModel by viewModel()

    companion object {
        fun start(baseActivity: BaseActivity?) {
            baseActivity?.startActivity(
                Intent(baseActivity, ManageGroupsActivity::class.java).apply {
                    putExtras(bundleOf(EXTRA_NAVIGATION_TRANSITION to NavTransition.MODAL))
                }
            )
        }
    }

    override fun initViews() {
        super.initViews()
        toolbar.setOnCloseClick(::onCloseClick)
        setSupportActionBar(toolbar)
        supportActionBar?.run { title = getString(R.string.profile_admin_groups_title) }
        addButtonEmptyState.setOnClickListener { onAddGroupClick() }
    }

    override fun initObservers() {
        super.initObservers()
        observe(viewModel.loaderState) { if (it) showLoader() else hideLoader() }
        observe(viewModel.user) {
            if (it != null && it.userGroups.isNotEmpty()) {
                groupsRecyclerView.layoutManager = LinearLayoutManager(this)
                groupsRecyclerView.adapter = ManageGroupsAdapter(
                    it,
                    it.userGroups.sortedBy { group -> group.name },
                    ::onAddGroupClick,
                    ::onGroupLongClick,
                    ::onGroupClick
                )
            }
            showEmptyState(it == null || it.userGroups.isEmpty())
        }
        observe(viewModel.isUserAdminOf) {
            GroupDetailsBottomSheet(
                group = it.first,
                userId = it.second,
                onDismiss = ::loadData
            ).show(
                supportFragmentManager
            )
        }
    }

    override fun loadData() = viewModel.fetchUser()

    private fun showEmptyState(show: Boolean) {
        groupsRecyclerView.visibility(!show)
        emptyState.visibility(show)
        addButtonEmptyState.visibility(show)
    }

    private fun onAddGroupClick() = AddGroupDialog(
        ::onAddGroup
    ).show(supportFragmentManager)

    private fun onAddGroup(groupName: String) {
        viewModel.addGroup(groupName)
    }

    private fun onGroupClick(group: UserGroup) {
        viewModel.isUserAdminOf(group)
    }

    private fun onGroupLongClick(group: UserGroup) =
        popup(
            fm = supportFragmentManager,
            model = PopupModel(
                title = getString(R.string.confirmation),
                message = getString(R.string.profile_change_selected_group_confirmation),
                buttonPositive = getString(R.string.select),
                buttonNegative = getString(R.string.cancel),
                buttonPositiveListener = { onChangeGroup(group) },
                isCancelable = true
            )
        )

    private fun onChangeGroup(group: UserGroup) {
        viewModel.changeSelectedGroup(group)
    }

    private fun onCloseClick() = onBackPressed()

    override fun getLayoutRes(): Int = R.layout.activity_manage_groups
}