package revolhope.splanes.com.aikver.presentation.feature.menu.addcontent

import androidx.appcompat.widget.SearchView
import kotlinx.android.synthetic.main.fragment_add_content.contentSelector
import kotlinx.android.synthetic.main.fragment_add_content.searchView
import org.koin.android.viewmodel.ext.android.viewModel
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.presentation.common.base.BaseFragment

class AddContentFragment : BaseFragment(), SearchView.OnQueryTextListener {

    private val viewModel: AddContentViewModel by viewModel()

    override fun initViews() {
        searchView.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrBlank()) viewModel.fetchContent(query, contentSelector.selection.value)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean = true /* Nothing to do here */

    override fun getLayoutResource(): Int = R.layout.fragment_add_content
}
