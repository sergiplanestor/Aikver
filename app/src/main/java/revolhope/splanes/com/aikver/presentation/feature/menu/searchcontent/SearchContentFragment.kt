package revolhope.splanes.com.aikver.presentation.feature.menu.searchcontent

import android.view.animation.AnimationUtils
import androidx.appcompat.widget.SearchView
import kotlinx.android.synthetic.main.fragment_search_content.contentSelector
import kotlinx.android.synthetic.main.fragment_search_content.imageViewPlaceholder
import kotlinx.android.synthetic.main.fragment_search_content.placeholder
import kotlinx.android.synthetic.main.fragment_search_content.resultsRecyclerView
import kotlinx.android.synthetic.main.fragment_search_content.searchView
import kotlinx.android.synthetic.main.fragment_search_content.textViewPlaceholder
import org.koin.android.viewmodel.ext.android.viewModel
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.framework.app.hideKeyboard
import revolhope.splanes.com.aikver.framework.app.observe
import revolhope.splanes.com.aikver.presentation.common.base.BaseActivity
import revolhope.splanes.com.aikver.presentation.common.base.BaseFragment
import revolhope.splanes.com.aikver.presentation.common.visibility
import revolhope.splanes.com.aikver.presentation.common.widget.gridlayoutmanager.AutoSizeLayoutManager
import revolhope.splanes.com.aikver.presentation.feature.menu.common.content.ContentDetailsActivity
import revolhope.splanes.com.core.domain.model.content.Content

class SearchContentFragment : BaseFragment(), SearchView.OnQueryTextListener {

    private val viewModel: SearchContentViewModel by viewModel()

    override fun onResume() {
        super.onResume()
        resultsRecyclerView.requestFocus()
        hideKeyboard()
    }

    override fun initViews() {
        searchView.setOnQueryTextListener(this)
    }

    override fun initObservers() {
        observe(viewModel.loaderState) { if (it) showLoader() else hideLoader() }
        observe(viewModel.serieResults) { onResultsReceived(it, ::onContentClick) }
        observe(viewModel.movieResults) { onResultsReceived(it, ::onContentClick) }
    }

    private fun <T : Content> onResultsReceived(results: List<T>, onClick: (T) -> Unit) {
        if (results.isNotEmpty() && context != null) {
            resultsRecyclerView.layoutManager = AutoSizeLayoutManager(requireContext(), 120f)
            resultsRecyclerView.layoutAnimation =
                AnimationUtils.loadLayoutAnimation(context, R.anim.recycler_falldown)
            resultsRecyclerView.adapter = SearchContentAdapter(results, onClick)
        }
        resultsRecyclerView.visibility(results.isNotEmpty())
        showNotFound(results.isEmpty())
    }

    private fun showNotFound(shouldShow: Boolean) {
        if (shouldShow) {
            imageViewPlaceholder.setImageResource(R.drawable.ic_no_result)
            textViewPlaceholder.text = getString(R.string.add_content_search_no_result)
        }
        placeholder.visibility(shouldShow)
    }

    private fun <T : Content> onContentClick(item: T) {
        ContentDetailsActivity.start(activity as? BaseActivity, item)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrBlank()) viewModel.fetchContent(query, contentSelector.selection.value)
        hideKeyboard()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean = true /* Nothing to do here */

    override fun getLayoutResource(): Int = R.layout.fragment_search_content
}
