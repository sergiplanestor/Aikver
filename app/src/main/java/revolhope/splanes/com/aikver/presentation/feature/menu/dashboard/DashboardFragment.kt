package revolhope.splanes.com.aikver.presentation.feature.menu.dashboard

import kotlinx.android.synthetic.main.fragment_dashboard.popularPager
import org.koin.android.viewmodel.ext.android.viewModel
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.framework.app.observe
import revolhope.splanes.com.aikver.presentation.common.base.BaseFragment

class DashboardFragment : BaseFragment() {

    private val viewModel: DashboardViewModel by viewModel()

    override fun initViews() {
        super.initViews()

    }

    override fun initObservers() {
        super.initObservers()
        observe(viewModel.popularContent) { popularPager.addItems(it) }
    }

    override fun loadData() {
        super.loadData()
        viewModel.fetchPopularContent()
    }

    override fun getLayoutResource(): Int = R.layout.fragment_dashboard
}
