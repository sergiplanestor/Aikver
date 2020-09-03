package revolhope.splanes.com.aikver.presentation.feature.menu.dashboard

import androidx.lifecycle.LiveData
import kotlinx.android.synthetic.main.fragment_dashboard.contentShimmer
import kotlinx.android.synthetic.main.fragment_dashboard.groupContentEmptyState
import kotlinx.android.synthetic.main.fragment_dashboard.groupContentRecycler
import kotlinx.android.synthetic.main.fragment_dashboard.popularPager
import kotlinx.android.synthetic.main.fragment_dashboard.recommendedPager
import kotlinx.android.synthetic.main.fragment_dashboard.swipeLayout
import org.koin.android.viewmodel.ext.android.viewModel
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.framework.app.observe
import revolhope.splanes.com.aikver.presentation.common.base.BaseActivity
import revolhope.splanes.com.aikver.presentation.common.base.BaseFragment
import revolhope.splanes.com.aikver.presentation.common.invisible
import revolhope.splanes.com.aikver.presentation.common.visibility
import revolhope.splanes.com.aikver.presentation.common.visible
import revolhope.splanes.com.aikver.presentation.common.widget.gridlayoutmanager.AutoSizeLayoutManager
import revolhope.splanes.com.aikver.presentation.common.widget.swipelayout.SwipeLayout
import revolhope.splanes.com.aikver.presentation.feature.menu.MenuActivity
import revolhope.splanes.com.aikver.presentation.feature.menu.common.contentdetails.ContentDetailsActivity
import revolhope.splanes.com.aikver.presentation.feature.menu.common.customcontent.CustomContentDetailsActivity
import revolhope.splanes.com.core.domain.model.content.Content
import revolhope.splanes.com.core.domain.model.content.ContentDetails
import revolhope.splanes.com.core.domain.model.content.CustomContent

class DashboardFragment : BaseFragment() {

    private val viewModel: DashboardViewModel by viewModel()

    override fun initViews() {
        super.initViews()
        contentShimmer.visible()
        groupContentRecycler.invisible()
        swipeLayout.setOnRefreshListener(object : SwipeLayout.OnCircleRefreshListener {
            override fun completeRefresh() {
            }

            override fun refreshing() {
                initViews()
                viewModel.fetchGroupContent(force = true)
            }
        })
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
            contentShimmer.invisible()
            if (it.isEmpty()) {
                showContentEmptyState()
            } else {
                groupContentRecycler.visible()
                groupContentRecycler.layoutManager = AutoSizeLayoutManager(
                    context = requireContext(),
                    defaultWidth = 120f
                )
                groupContentRecycler.adapter = GroupContentAdapter(
                    items = it,
                    onItemClick = ::onCustomContentClick
                )
            }
            swipeLayout.finishRefreshing()
        }
    }

    override fun loadData() {
        super.loadData()
        viewModel.fetchPopularContent()
        viewModel.fetchGroupContent()
    }

    private fun showContentEmptyState() {
        groupContentRecycler.invisible()
        groupContentEmptyState.visible()
        groupContentEmptyState.setAction {
            (requireActivity() as? MenuActivity)?.navigate(R.id.navigation_search_content)
        }
    }

    private fun onPopularContentClick(content: Content) {
        ContentDetailsActivity.start(requireActivity() as? BaseActivity, content)
    }

    private fun onCustomContentClick(customContent: CustomContent<ContentDetails>) {
        CustomContentDetailsActivity.start(requireActivity() as? BaseActivity, customContent)
    }

    override fun getErrorLiveData(): LiveData<String>? = viewModel.errorState

    override fun getLayoutResource(): Int = R.layout.fragment_dashboard
}
