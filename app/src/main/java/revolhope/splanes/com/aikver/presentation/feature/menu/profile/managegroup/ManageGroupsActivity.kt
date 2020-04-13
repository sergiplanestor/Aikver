package revolhope.splanes.com.aikver.presentation.feature.menu.profile.managegroup

import android.content.Intent
import androidx.core.os.bundleOf
import kotlinx.android.synthetic.main.activity_manage_groups.toolbar
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.presentation.common.base.BaseActivity

class ManageGroupsActivity : BaseActivity() {

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