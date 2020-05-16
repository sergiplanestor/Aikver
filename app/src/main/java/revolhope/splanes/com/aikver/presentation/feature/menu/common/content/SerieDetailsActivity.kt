package revolhope.splanes.com.aikver.presentation.feature.menu.common.content

import android.annotation.SuppressLint
import android.content.Intent
import android.transition.TransitionManager
import android.view.View
import android.view.ViewTreeObserver
import androidx.core.os.bundleOf
import kotlinx.android.synthetic.main.activity_content_details.collapsingToolbarImageView
import kotlinx.android.synthetic.main.activity_content_details.companyImage
import kotlinx.android.synthetic.main.activity_content_details.episodesTextView
import kotlinx.android.synthetic.main.activity_content_details.genresTextView
import kotlinx.android.synthetic.main.activity_content_details.originalTitleTextView
import kotlinx.android.synthetic.main.activity_content_details.overviewDecorator
import kotlinx.android.synthetic.main.activity_content_details.overviewTextView
import kotlinx.android.synthetic.main.activity_content_details.overviewVisibilityButton
import kotlinx.android.synthetic.main.activity_content_details.overviewWrapLayout
import kotlinx.android.synthetic.main.activity_content_details.seasonTextView
import kotlinx.android.synthetic.main.activity_content_details.statusTextView
import kotlinx.android.synthetic.main.activity_content_details.toolbar
import kotlinx.android.synthetic.main.activity_content_details.voteAverageTextView
import kotlinx.android.synthetic.main.activity_content_details.voteCardView
import kotlinx.android.synthetic.main.activity_content_details.voteTextView
import kotlinx.android.synthetic.main.activity_content_details.yearTextView
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.presentation.common.base.BaseActivity
import revolhope.splanes.com.aikver.presentation.common.loadUrl
import revolhope.splanes.com.core.domain.model.content.serie.Serie
import org.koin.android.viewmodel.ext.android.viewModel
import revolhope.splanes.com.aikver.framework.app.observe
import revolhope.splanes.com.aikver.presentation.common.invisible
import revolhope.splanes.com.aikver.presentation.common.justify
import revolhope.splanes.com.aikver.presentation.common.popupError
import revolhope.splanes.com.aikver.presentation.common.toCustomString
import revolhope.splanes.com.aikver.presentation.common.visible
import revolhope.splanes.com.core.domain.model.content.serie.SerieDetails
import revolhope.splanes.com.core.domain.model.content.serie.SerieStatus
import java.util.*

class SerieDetailsActivity : BaseActivity() {

    private val viewModel: SerieDetailsViewModel by viewModel()

    companion object {
        private const val OVERVIEW_COMPACT_LINES = 5
        private const val OVERVIEW_COMPACT_MAX_LINES = 7
        private const val EXTRA_CONTENT = "serieDetailsActivity.extra.content"

        fun start(baseActivity: BaseActivity?, serie: Serie) {
            baseActivity?.startActivity(
                Intent(baseActivity, SerieDetailsActivity::class.java).apply {
                    putExtras(
                        bundleOf(
                            EXTRA_NAVIGATION_TRANSITION to NavTransition.MODAL,
                            EXTRA_CONTENT to serie
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
            } else {
                bindViews(it)
            }
        }
    }

    override fun loadData() = viewModel.fetchDetails(getContent()?.id ?: -1)

    @SuppressLint("SetTextI18n")
    private fun bindViews(details: SerieDetails) {

        supportActionBar?.subtitle = details.firstAirDate.split("-")[0]

        /* Company image */
        if (details.network.isNotEmpty()) {
            companyImage.loadUrl(
                details.network.find {
                    it.name.toLowerCase(Locale.ROOT).contains("hbo", ignoreCase = true) ||
                            it.name.toLowerCase(Locale.ROOT)
                                .contains("netflix", ignoreCase = true) ||
                            it.name.toLowerCase(Locale.ROOT)
                                .contains("amazon", ignoreCase = true) ||
                            it.name.toLowerCase(Locale.ROOT).contains("movistar", ignoreCase = true)
                }?.logo ?: details.network.first().logo
            )
        } else {
            companyImage.invisible()
        }

        /* Year first air */
        if (!details.firstAirDate.isBlank()) {
            yearTextView.text = details.firstAirDate.split("-")[0]
        } else {
            yearTextView.invisible()
        }

        /* Num of seasons */
        if (details.numSeasons != -1) {
            seasonTextView.text =
                getString(R.string.content_details_seasons_info, details.numSeasons)
            if (details.numSeasons > 1) {
                seasonTextView.text = "${seasonTextView.text}s"
            }
        } else {
            seasonTextView.invisible()
        }

        /* Status */
        statusTextView.text = getStatusString(details.status)

        /* Punctuation */
        if (!details.voteAverage.isNaN() || details.voteAverage == 0f) {
            voteAverageTextView.text = details.voteAverage.toString()
            resizeVoteLayout(voteCardView)
        } else {
            voteCardView.invisible()
        }

        /* Original title */
        if (details.originCountry.isNotEmpty()) {
            originalTitleTextView.text = getString(
                R.string.content_details_original_title_info,
                details.originalName,
                details.originCountry.first().toUpperCase(Locale.ROOT)
            )
        } else {
            originalTitleTextView.text = details.originalName
        }

        /* Genres */
        genresTextView.text = details.genres.let { genres ->
            var t = ""
            genres.forEach { t += "${it.name}, " }
            t.removeSuffix(", ")
        }

        /* Overview */
        overviewTextView.text = details.overview
        overviewTextView.justify()
        overviewTextView.viewTreeObserver.addOnGlobalLayoutListener(
            object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    if (overviewTextView.lineCount > OVERVIEW_COMPACT_LINES) {
                        overviewTextView.maxLines = 7
                        overviewDecorator.visible()
                        overviewVisibilityButton.visible()
                        overviewVisibilityButton.setOnClickListener(::swapOverviewState)
                    }
                    overviewTextView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            }
        )

        /* Episodes */
        episodesTextView.text =
            getString(
                R.string.content_details_episodes_info2,
                details.numSeasons,
                details.numEpisodes,
                if (details.status != SerieStatus.UNKNOWN) {
                    "\n\n" + getString(
                        R.string.content_details_episodes_info1,
                        getStatusString(details.status).toLowerCase(Locale.ROOT)
                    )
                } else {
                    ""
                }
            )

        /* Popularity */
        voteTextView.text = getString(
            R.string.content_details_vote_info,
            details.popularity.toInt().toCustomString(),
            details.voteCount.toCustomString()
        )
    }

    private fun swapOverviewState(view: View) {
        if (overviewTextView.maxLines == OVERVIEW_COMPACT_MAX_LINES) {
            overviewTextView.maxLines = Int.MAX_VALUE
            overviewDecorator.invisible(isGone = false)
            overviewVisibilityButton.text = getString(R.string.content_details_overview_see_less)
        } else {
            overviewTextView.maxLines = OVERVIEW_COMPACT_MAX_LINES
            overviewDecorator.visible()
            overviewVisibilityButton.text = getString(R.string.content_details_overview_see_more)
        }
        TransitionManager.beginDelayedTransition(overviewWrapLayout)
    }

    private fun resizeVoteLayout(v: View) {
        v.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val size = if (v.measuredHeight > v.measuredWidth) {
                    v.measuredHeight
                } else {
                    v.measuredWidth
                }
                v.layoutParams = v.layoutParams.apply {
                    width = size
                    height = size
                }
                v.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }

    private fun getStatusString(status: SerieStatus): String =
        getString(
            when (status) {
                SerieStatus.ENDED -> R.string.content_details_status_ended
                SerieStatus.IN_PROGRESS -> R.string.content_details_status_in_progress
                SerieStatus.UNKNOWN -> R.string.content_details_status_unknown
            }
        )

    private fun getContent(): Serie? =
        intent.extras?.getSerializable(EXTRA_CONTENT) as Serie?

    override fun getLayoutRes(): Int = R.layout.activity_content_details
}