package revolhope.splanes.com.aikver.presentation.feature.menu.profile.managegroup

import kotlinx.android.synthetic.main.activity_manage_groups.toolbar
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.presentation.common.base.BaseActivity

class ManageGroupsActivity : BaseActivity() {

    override fun initViews() {
        super.initViews()
        toolbar.setOnCloseClick(::onCloseClick)
        setSupportActionBar(toolbar)
        supportActionBar?.run { title = "Administra tus grupos" }
    }

    override fun initObservers() {
        super.initObservers()
    }

    override fun loadData() {
        super.loadData()
    }

    private fun onCloseClick() = onBackPressed()

    override fun getLayoutRes(): Int = R.layout.activity_manage_groups
}