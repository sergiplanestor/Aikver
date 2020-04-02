package revolhope.splanes.com.aikver.presentation.dashboard

import android.content.Intent
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.domain.Serie
import revolhope.splanes.com.aikver.presentation.addserie.AddSerieActivity
import revolhope.splanes.com.aikver.presentation.common.base.BaseActivity
import revolhope.splanes.com.aikver.presentation.common.widget.filterbottomsheet.FiltersBottomSheet
import revolhope.splanes.com.aikver.presentation.common.widget.filterbottomsheet.FiltersModel
import revolhope.splanes.com.aikver.presentation.detailsserie.DetailsSerieBottomSheet
import revolhope.splanes.com.aikver.presentation.searchserie.SearchResultsActivity
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.include_search_view.*

class DashboardActivity : BaseActivity<DashboardViewModel>(), View.OnClickListener {

    companion object {
        var forceRestCall = false
    }

    override fun initializeViews() {
        super.initializeViews()
        finderSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                onFilterSet(FiltersModel(input = query))
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = true
        })
        filterButton.setOnClickListener(this)
        addButton.setOnClickListener(this)
    }

// =================================================================================================
// Services
// =================================================================================================

    override fun loadData() {
        super.loadData()
        fetchSeries(forceAllSeriesCall = true)
    }

    override fun reloadData() {
        super.reloadData()
        lastSerieViewer.showShimmer()
        allSerieViewer.showShimmer()
        fetchSeries(
            forceAllSeriesCall = forceRestCall
        )
        forceRestCall = false
    }

    private fun fetchSeries(
        forceAllSeriesCall: Boolean = false,
        forceRecentSeriesCall: Boolean = false
    ) {
        getViewModel().getAllSeries(forceAllSeriesCall).observe(
            this,
            Observer { allSeries ->
                allSerieViewer.updateItems(allSeries, ::onSerieClick)

                getViewModel().getRecentSeries(forceRecentSeriesCall).observe(
                    this,
                    Observer { lastSeries ->
                        lastSerieViewer.updateItems(lastSeries, ::onSerieClick)
                    }
                )
            }
        )
    }

    private fun onFilterSet(filterModel: FiltersModel) =
        navigateUp(
            SearchResultsActivity::class.java,
            bundleOf(Pair(SearchResultsActivity.EXTRA, filterModel))
        )

// =================================================================================================
// View.OnClickListener impl
// =================================================================================================

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.addButton -> navigateLateral(AddSerieActivity::class.java, isForResult = true)
            R.id.filterButton -> FiltersBottomSheet(::onFilterSet).show(supportFragmentManager)
        }
    }

    private fun onSerieClick(serie: Serie) =
        DetailsSerieBottomSheet(serie).show(supportFragmentManager)

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        forceRestCall = true
    }

// =================================================================================================
// BaseActivity abstract impl
// =================================================================================================

    override fun getLayoutRes(): Int = R.layout.activity_dashboard

    override fun getViewModelClass(): Class<DashboardViewModel> = DashboardViewModel::class.java
}