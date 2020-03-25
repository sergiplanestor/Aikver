package bemobile.splanes.com.aikver.presentation.addserie

import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import androidx.lifecycle.Observer
import bemobile.splanes.com.aikver.R
import bemobile.splanes.com.aikver.presentation.common.base.BaseActivity
import bemobile.splanes.com.aikver.presentation.common.widget.imagepager.ImagePager
import bemobile.splanes.com.aikver.presentation.common.widget.popup.Popup
import kotlinx.android.synthetic.main.activity_add_serie.*

class AddSerieActivity : BaseActivity<AddSerieViewModel>(), View.OnClickListener {

    override fun initializeViews() {
        super.initializeViews()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        searchButton.setOnClickListener(this)
        addButton.setOnClickListener(this)
    }

// =================================================================================================
// Private methods
// =================================================================================================

    private fun showPager(links: List<String>) {

        TransitionManager.beginDelayedTransition(textInputLayout, AutoTransition().apply {
            duration = 400
        })

        if (textInputLayout.childCount > 1) {
            textInputLayout.removeViewAt(1)
        }

        textInputLayout.addView(
            ImagePager(this).apply {
                initialize(links)
            }
        )
    }

    private fun clearTitleLayoutError() {
        titleLayout.error = null
    }

    private fun addSerie() {
        showLoader()
        getViewModel().addSerie(
            titleEditText.text?.toString(),
            (textInputLayout.getChildAt(1) as ImagePager?)?.getSelectedLink(),
            platformViewer.getSelectedPlatform(),
            categorySpinner.getSelected(),
            scoreView.getScore()
        ).observe(this, Observer { result ->

            if (result) {
                hideLoader()
                finishPush()
            } else {
                Popup.showError(this, supportFragmentManager, View.OnClickListener {  })
            }
        })
    }

// =================================================================================================
// View.OnClickListener impl
// =================================================================================================

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.searchButton -> onSearchClick()
            R.id.addButton -> onAddClick()
        }
    }

    private fun onSearchClick() {
        val query = titleEditText.text?.toString()
        if (query != null && query.isNotBlank()) {
            showLoader()
            getViewModel().searchImages(query).observe(this, Observer { links ->
                showPager(links)
                hideLoader()
            })
        }
    }

    private fun onAddClick() {
        clearTitleLayoutError()
        val title = titleEditText.text?.toString()
        if (title.isNullOrBlank()) {
            titleLayout.error = getString(R.string.error_blank_field)
        } else {
            addSerie()
        }
    }

// =================================================================================================
// BaseActivity abstract impl
// =================================================================================================

    override fun getLayoutRes(): Int = R.layout.activity_add_serie

    override fun getViewModelClass(): Class<AddSerieViewModel> = AddSerieViewModel::class.java
}