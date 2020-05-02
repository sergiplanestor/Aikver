package revolhope.splanes.com.aikver.presentation.feature.menu.common.content

import android.content.Intent
import androidx.core.os.bundleOf
import kotlinx.android.synthetic.main.activity_content_details.collapsingToolbarImageView
import kotlinx.android.synthetic.main.activity_content_details.toolbar
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.presentation.common.base.BaseActivity
import revolhope.splanes.com.aikver.presentation.common.loadUrl
import revolhope.splanes.com.core.domain.model.content.Content
import revolhope.splanes.com.core.domain.model.content.movie.Movie
import revolhope.splanes.com.core.domain.model.content.serie.Serie

class ContentDetailsActivity : BaseActivity() {

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
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun initObservers() {

    }

    private fun getContent(): Content? =
        intent.extras?.getSerializable(EXTRA_CONTENT).let {
            if (getContentType() == 0) it as Movie else it as Serie
        }

    private fun getContentType(): Int = intent.extras?.getInt(EXTRA_CONTENT_TYPE) ?: 1

    override fun getLayoutRes(): Int = R.layout.activity_content_details
}