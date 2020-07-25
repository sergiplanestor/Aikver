package revolhope.splanes.com.aikver.presentation.feature.menu.dashboard

import kotlinx.android.synthetic.main.fragment_dashboard.groupContentRecycler
import kotlinx.android.synthetic.main.fragment_dashboard.popularPager
import kotlinx.android.synthetic.main.fragment_dashboard.recommendedPager
import org.koin.android.viewmodel.ext.android.viewModel
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.framework.app.observe
import revolhope.splanes.com.aikver.presentation.common.base.BaseFragment
import revolhope.splanes.com.aikver.presentation.common.visibility
import revolhope.splanes.com.aikver.presentation.common.widget.gridlayoutmanager.AutoSizeLayoutManager
import revolhope.splanes.com.core.domain.model.content.ContentDetails
import revolhope.splanes.com.core.domain.model.content.CustomContent

class DashboardFragment : BaseFragment() {

    private val viewModel: DashboardViewModel by viewModel()

    override fun initViews() {
        super.initViews()

    }

    override fun initObservers() {
        super.initObservers()
        observe(viewModel.popularContent) {
            popularPager.addContentItems(it, ::onContentClick)
        }
        observe(viewModel.recommendedContent) {
            recommendedPager.visibility(it.isNotEmpty())
            recommendedPager.addContentDetailsItems(it, ::onContentClick)
        }
        observe(viewModel.groupContent) {
            groupContentRecycler.layoutManager = AutoSizeLayoutManager(
                context = requireContext(),
                defaultWidth = 120f
            )
            groupContentRecycler.adapter = GroupContentAdapter(
                items = it,
                onItemClick = ::onGroupContentClick
            )
        }
    }

    override fun loadData() {
        super.loadData()
        viewModel.fetchPopularContent()
        viewModel.fetchGroupContent()
    }

    private fun onContentClick(id: String) {

    }

    private fun onGroupContentClick(content: CustomContent<ContentDetails>) {

    }

    override fun getLayoutResource(): Int = R.layout.fragment_dashboard
}
