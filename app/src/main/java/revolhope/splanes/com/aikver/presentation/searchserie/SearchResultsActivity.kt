package revolhope.splanes.com.aikver.presentation.searchserie

import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.domain.Serie
import revolhope.splanes.com.aikver.presentation.common.base.BaseActivity
import revolhope.splanes.com.aikver.presentation.common.widget.filterbottomsheet.FiltersBottomSheet
import revolhope.splanes.com.aikver.presentation.common.widget.filterbottomsheet.FiltersModel
import revolhope.splanes.com.aikver.presentation.detailsserie.DetailsSerieBottomSheet
import kotlinx.android.synthetic.main.activity_search_result.*
import kotlinx.android.synthetic.main.include_search_view.*

class SearchResultsActivity : BaseActivity<SearchResultsViewModel>(), View.OnClickListener {

    companion object {
        const val EXTRA = "filter.model.extra"
    }

    override fun initializeViews() {
        super.initializeViews()
        val model = intent.extras?.getSerializable(EXTRA) as FiltersModel?
        if (model?.input != null) {
            finderSearchView.setQuery(model.input, false)
        }

        finderSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                observeFilters(FiltersModel(input = query))
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = true
        })
        filterButton.setOnClickListener(this)
    }

    override fun loadData() {
        super.loadData()
        val model = intent.extras?.getSerializable(EXTRA) as FiltersModel?
        if (model != null) observeFilters(model)
    }

// =================================================================================================
// Options menu
// =================================================================================================

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.close_menu, menu)
        return true
    }

// =================================================================================================
// Clicks
// =================================================================================================

    private fun onSerieClick(serie: Serie) =
        DetailsSerieBottomSheet(serie).show(supportFragmentManager)

    override fun onClick(view: View?) {
        if (view?.id == R.id.filterButton)
            FiltersBottomSheet(
                ::observeFilters,
                intent.extras?.getSerializable(EXTRA) as FiltersModel?
            ).show(supportFragmentManager)
    }

// =================================================================================================
// Filter response
// =================================================================================================

    private fun observeFilters(model: FiltersModel) {
        getViewModel().search(model = model).observe(this, Observer {
            resultRecyclerView.layoutManager = LinearLayoutManager(this)
            resultRecyclerView.adapter = SerieResultAdapter(it, ::onSerieClick)
        })
    }

// =================================================================================================
// Abstract methods impl
// =================================================================================================

    override fun getLayoutRes(): Int = R.layout.activity_search_result

    override fun getViewModelClass(): Class<SearchResultsViewModel> =
        SearchResultsViewModel::class.java
}