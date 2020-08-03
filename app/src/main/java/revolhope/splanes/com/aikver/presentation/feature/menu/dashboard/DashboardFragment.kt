package revolhope.splanes.com.aikver.presentation.feature.menu.dashboard

import kotlinx.android.synthetic.main.fragment_dashboard.groupContentRecycler
import kotlinx.android.synthetic.main.fragment_dashboard.popularPager
import kotlinx.android.synthetic.main.fragment_dashboard.recommendedPager
import org.koin.android.viewmodel.ext.android.viewModel
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.framework.app.observe
import revolhope.splanes.com.aikver.presentation.common.base.BaseActivity
import revolhope.splanes.com.aikver.presentation.common.base.BaseFragment
import revolhope.splanes.com.aikver.presentation.common.visibility
import revolhope.splanes.com.aikver.presentation.common.widget.gridlayoutmanager.AutoSizeLayoutManager
import revolhope.splanes.com.aikver.presentation.feature.menu.common.contentdetails.ContentDetailsActivity
import revolhope.splanes.com.aikver.presentation.feature.menu.common.customcontent.CustomContentDetailsActivity
import revolhope.splanes.com.core.domain.model.content.Content
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
            popularPager.addContentItems(it, ::onPopularContentClick)
        }
        observe(viewModel.recommendedContent) {
            recommendedPager.visibility(it.isNotEmpty())
            recommendedPager.addContentDetailsItems(it, ::onCustomContentClick)
        }
        observe(viewModel.groupContent) {
            groupContentRecycler.layoutManager = AutoSizeLayoutManager(
                context = requireContext(),
                defaultWidth = 120f
            )
            groupContentRecycler.adapter = GroupContentAdapter(
                items = it,
                onItemClick = ::onCustomContentClick
            )
        }
    }

    override fun loadData() {
        super.loadData()
        viewModel.fetchPopularContent()
        viewModel.fetchGroupContent()
    }

    private fun onPopularContentClick(content: Content) {
        ContentDetailsActivity.start(requireActivity() as? BaseActivity, content)
    }

    private fun onCustomContentClick(customContent: CustomContent<ContentDetails>) {
        CustomContentDetailsActivity.start(requireActivity() as? BaseActivity, customContent)
    }

    override fun getLayoutResource(): Int = R.layout.fragment_dashboard
}
