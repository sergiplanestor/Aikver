package revolhope.splanes.com.aikver.presentation.feature.menu.common.content

import android.content.Intent
import androidx.core.os.bundleOf
import kotlinx.android.synthetic.main.activity_content_details.collapsingToolbarImageView
import kotlinx.android.synthetic.main.activity_content_details.genresTextView
import kotlinx.android.synthetic.main.activity_content_details.originalTitleTextView
import kotlinx.android.synthetic.main.activity_content_details.overviewTextView
import kotlinx.android.synthetic.main.activity_content_details.toolbar
import kotlinx.android.synthetic.main.activity_content_details.voteAverageTextView
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.presentation.common.base.BaseActivity
import revolhope.splanes.com.aikver.presentation.common.loadUrl
import revolhope.splanes.com.core.domain.model.content.Content
import revolhope.splanes.com.core.domain.model.content.movie.Movie
import revolhope.splanes.com.core.domain.model.content.serie.Serie
import org.koin.android.viewmodel.ext.android.viewModel
import revolhope.splanes.com.aikver.framework.app.observe
import revolhope.splanes.com.aikver.presentation.common.justify
import revolhope.splanes.com.aikver.presentation.common.popupError
import revolhope.splanes.com.core.domain.model.content.serie.SerieDetails

class ContentDetailsActivity : BaseActivity() {

    private val viewModel: ContentDetailsViewModel by viewModel()

    companion object {

        private const val EXTRA_CONTENT = "extra.content"
        private const val EXTRA_CONTENT_TYPE = "extra.content.type"

        fun <T : Content> start(baseActivity: BaseActivity?, item: T) {
            baseActivity?.startActivity(
                Intent(baseActivity, ContentDetailsActivity::class.java).apply {
                    putExtras(
                        bundleOf(
                            EXTRA_NAVIGATION_TRANSITION to NavTransition.MODAL,
                            EXTRA_CONTENT to item,
                            EXTRA_CONTENT_TYPE to if (item is Movie) 0 else 1
                        )
                    )
                }
            )
        }
    }

    override fun initViews() {
        collapsingToolbarImageView.loadUrl(
            getContent()?.backdrop.let {
                if (it.isNullOrBlank()) getContent()?.thumbnail ?: "" else it
            }
        )
        setSupportActionBar(toolbar)
        supportActionBar?.title = getContent()?.title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun initObservers() {
        observe(viewModel.loaderState) { if (it) showLoader() else hideLoader() }
        observe(viewModel.serieDetails) {
            if (it == null) {
                popupError(
                    context = this,
                    fm = supportFragmentManager,
                    action = ::onBackPressed
                )
            }
            else {
                bindViews(it)
            }
        }
    }

    override fun loadData() = viewModel.fetchDetails(getContent()?.id ?: -1)

    private fun bindViews(details: SerieDetails) {
        voteAverageTextView.text = details.voteAverage.toString()
        originalTitleTextView.text = "${details.originalName} (${details.originalLanguage})"
        genresTextView.text = details.genres.let { genres ->
            var t = ""
            genres.forEach { t += "${it.name}, " }
            t.removeSuffix(", ")
        }
        overviewTextView.text = details.overview

        overviewTextView.justify()
    }

    private fun getContent(): Content? =
        intent.extras?.getSerializable(EXTRA_CONTENT).let {
            if (getContentType() == 0) it as Movie else it as Serie
        }

    private fun getContentType(): Int = intent.extras?.getInt(EXTRA_CONTENT_TYPE) ?: 1

    override fun getLayoutRes(): Int = R.layout.activity_content_details
}