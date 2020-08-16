package revolhope.splanes.com.aikver.presentation.feature.menu.common.customcontent

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import android.view.ViewTreeObserver
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_custom_content_details.collapsingToolbarImageView
import kotlinx.android.synthetic.main.activity_custom_content_details.companyImage
import kotlinx.android.synthetic.main.activity_custom_content_details.contentRecycler
import kotlinx.android.synthetic.main.activity_custom_content_details.customContentView
import kotlinx.android.synthetic.main.activity_custom_content_details.extraInfo1TextView
import kotlinx.android.synthetic.main.activity_custom_content_details.extraInfo2TextView
import kotlinx.android.synthetic.main.activity_custom_content_details.relatedContentGroup
import kotlinx.android.synthetic.main.activity_custom_content_details.relatedContentRecycler
import kotlinx.android.synthetic.main.activity_custom_content_details.relatedContentTextView
import kotlinx.android.synthetic.main.activity_custom_content_details.statusTextView
import kotlinx.android.synthetic.main.activity_custom_content_details.toolbar
import kotlinx.android.synthetic.main.activity_custom_content_details.voteAverageTextView
import kotlinx.android.synthetic.main.activity_custom_content_details.voteCardView
import org.koin.android.viewmodel.ext.android.viewModel
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.framework.app.observe
import revolhope.splanes.com.aikver.presentation.common.base.BaseActivity
import revolhope.splanes.com.aikver.presentation.common.invisible
import revolhope.splanes.com.aikver.presentation.common.loadUrl
import revolhope.splanes.com.aikver.presentation.common.toCustomString
import revolhope.splanes.com.aikver.presentation.common.visible
import revolhope.splanes.com.aikver.presentation.feature.menu.common.contentdetails.ContentDetailsActivity
import revolhope.splanes.com.aikver.presentation.feature.menu.common.contentdetails.adapter.ContentDetailsAdapter
import revolhope.splanes.com.aikver.presentation.feature.menu.common.contentdetails.adapter.ContentDetailsUiModel
import revolhope.splanes.com.aikver.presentation.feature.menu.common.relatedcontent.RelatedContentAdapter
import revolhope.splanes.com.aikver.presentation.feature.menu.common.relatedcontent.RelatedContentLayoutManager
import revolhope.splanes.com.core.domain.model.content.Content
import revolhope.splanes.com.core.domain.model.content.ContentDetails
import revolhope.splanes.com.core.domain.model.content.ContentStatus
import revolhope.splanes.com.core.domain.model.content.CustomContent
import revolhope.splanes.com.core.domain.model.content.QueriedContent
import revolhope.splanes.com.core.domain.model.content.movie.MovieDetails
import revolhope.splanes.com.core.domain.model.content.movie.QueriedMovies
import revolhope.splanes.com.core.domain.model.content.serie.QueriedSeries
import revolhope.splanes.com.core.domain.model.content.serie.SerieDetails
import revolhope.splanes.com.core.domain.model.user.User
import java.util.*

class CustomContentDetailsActivity : BaseActivity() {

    private val viewModel: CustomContentDetailsViewModel by viewModel()
    private var customContent: CustomContent<ContentDetails>? = null

    companion object {

        private const val EXTRA_CONTENT = "CustomContentActivity.extra.content"

        fun start(baseActivity: BaseActivity?, customContent: CustomContent<ContentDetails>) {
            baseActivity?.startActivity(
                Intent(baseActivity, CustomContentDetailsActivity::class.java).apply {
                    putExtras(
                        bundleOf(
                            EXTRA_NAVIGATION_TRANSITION to NavTransition.MODAL,
                            EXTRA_CONTENT to customContent
                        )
                    )
                }
            )
        }
    }

    override fun initViews() {
        super.initViews()
        getCustomContent()?.content?.let {
            collapsingToolbarImageView.loadUrl(
                if (it.backdrop.isBlank()) it.thumbnail else it.backdrop
            )
            setupToolbar(it)
            bindViews(it)
        }
    }

    override fun initObservers() {
        observe(viewModel.loaderState) { if (it) showLoader() else hideLoader() }
        observe(viewModel.contentRelated) {
            if (it == null || it.results.isEmpty()) {
                relatedContentGroup.invisible()
            } else {
                bindRelatedContent(it)
            }
        }
        observe(viewModel.user) {
            it?.let { user ->
                getCustomContent()?.let { content ->
                    customContentView.initialize(
                        currentUser = user,
                        customContent = content,
                        onContentSeenByClick = ::onContentSeenBy,
                        fragmentManager = supportFragmentManager
                    )
                }
            }
        }
        observe(viewModel.contentSeenByResponse) {
            customContentView.onSerieSeenByResponse(it.toMutableList())
        }
    }

    override fun loadData() {
        getCustomContent()?.let {
            viewModel.fetchContentRelated(it.content.id, it.content is SerieDetails)
        }
        viewModel.fetchUser()
    }

    private fun setupToolbar(details: ContentDetails) {
        setSupportActionBar(toolbar)
        supportActionBar?.title = getCustomContent()?.content?.title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        supportActionBar?.subtitle =
            when (details) {
                is SerieDetails -> details.firstAirDate.split("-")[0]
                is MovieDetails -> details.tagLine
                else -> null
            }
    }

    @SuppressLint("SetTextI18n")
    private fun bindViews(details: ContentDetails) {

        /* Toolbar extra info */
        bindBottomToolbarInfo(details)

        /* Recycler content info */
        contentRecycler.layoutManager = LinearLayoutManager(this)
        contentRecycler.adapter = ContentDetailsAdapter(buildDetailsAdapterData(details))

        /* Punctuation */
        if (!details.voteAverage.isNaN() || details.voteAverage == 0f) {
            voteAverageTextView.text = details.voteAverage.toString()
            resizeVoteLayout(voteCardView)
        } else {
            voteCardView.invisible()
        }
    }

    private fun bindBottomToolbarInfo(details: ContentDetails) {

        /* Serie Company image */
        if (details is SerieDetails && details.network.isNotEmpty()) {
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

        /* Release date */
        extraInfo1TextView.text = when (details) {
            is SerieDetails -> details.firstAirDate.split("-")[0]
            is MovieDetails -> details.releaseDate.split("-")[0]
            else -> null
        }

        /* Num of seasons (Serie) // Runtime (Movie) */
        extraInfo2TextView.text = when (details) {
            is SerieDetails -> {
                if (details.numSeasons != -1) {
                    getString(R.string.content_details_seasons_info, details.numSeasons).let {
                        if (details.numSeasons > 1) return@let it + "s"
                        return@let it
                    }
                } else {
                    null
                }
            }
            is MovieDetails -> getString(R.string.content_details_movie_runtime, details.runtime)
            else -> null
        }


        /* Status */
        statusTextView.text = getStatusString(details.status)
    }

    private fun buildDetailsAdapterData(details: ContentDetails): List<ContentDetailsUiModel> {
        val data = mutableListOf<ContentDetailsUiModel>()

        /* Original title */
        data.add(
            ContentDetailsUiModel(
                title = getString(R.string.content_details_original_title),
                content = if (details is SerieDetails && details.originCountry.isNotEmpty()) {
                    getString(
                        R.string.content_details_original_title_info,
                        details.originalTitle,
                        details.originCountry.first().toUpperCase(Locale.ROOT)
                    )
                } else if (details is MovieDetails && details.originalLanguage.isNotBlank()) {
                    getString(
                        R.string.content_details_original_title_info,
                        details.originalTitle,
                        details.originalLanguage.toUpperCase(Locale.ROOT)
                    )
                } else {
                    details.originalTitle
                }
            )
        )

        /* Genres */
        data.add(
            ContentDetailsUiModel(
                title = getString(R.string.content_details_genres_title),
                content = details.genres.let { genres ->
                    var t = ""
                    genres.forEach { t += "${it.name}, " }
                    t.removeSuffix(", ")
                }
            )
        )

        /* Overview */
        data.add(
            ContentDetailsUiModel(
                title = getString(R.string.content_details_overview_title),
                content = details.overview,
                isOverview = true
            )
        )

        /* Serie episodes */
        if (details is SerieDetails) {
            data.add(
                ContentDetailsUiModel(
                    title = getString(R.string.content_details_episodes_title),
                    content = getString(
                        R.string.content_details_episodes_info2,
                        details.numSeasons.toString(),
                        details.numEpisodes.toString(),
                        if (details.status != ContentStatus.UNKNOWN) {
                            "\n\n" + getString(
                                R.string.content_details_episodes_info1,
                                getStatusString(details.status).toLowerCase(Locale.ROOT)
                            )
                        } else {
                            ""
                        }
                    )
                )
            )
        }

        /* Punctuation */
        data.add(
            ContentDetailsUiModel(
                title = getString(R.string.content_details_vote_title),
                content = getString(
                    R.string.content_details_vote_info,
                    details.popularity.toInt().toCustomString(),
                    details.voteCount.toCustomString()
                )
            )
        )

        if (details.homepage.isNotBlank()) {
            data.add(
                ContentDetailsUiModel(
                    title = getString(R.string.content_details_homepage),
                    content = details.homepage,
                    isLink = true
                )
            )
        }

        return data
    }

    private fun bindRelatedContent(relatedContent: QueriedContent) {
        relatedContentGroup.visible()
        relatedContentTextView.text = getString(
            R.string.content_details_related_content_title,
            when (relatedContent) {
                is QueriedSeries -> getString(R.string.series)
                is QueriedMovies -> getString(R.string.films)
                else -> ""
            }
        )
        if (relatedContent.page == 1) {
            relatedContentRecycler.layoutManager = RelatedContentLayoutManager(this) {
                getCustomContent()?.content?.let {
                    viewModel.fetchContentRelated(
                        it.id,
                        it is SerieDetails,
                        relatedContent.page + 1
                    )
                    // TODO: Show any loader?
                }
            }
            relatedContentRecycler.adapter = RelatedContentAdapter(
                relatedContent.results.toMutableList(),
                ::onContentRelatedClick
            )
        } else {
            (relatedContentRecycler.adapter as RelatedContentAdapter).addItems(relatedContent.results)
        }
    }

    private fun onContentRelatedClick(content: Content) {
        ContentDetailsActivity.start(this, content)
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

    private fun onContentSeenBy(user: User, customContent: CustomContent<ContentDetails>) {
        viewModel.onContentSeenBy(user, customContent)
    }

    private fun getStatusString(status: ContentStatus): String =
        getString(
            when (status) {
                /* Serie status */
                ContentStatus.ENDED -> R.string.content_details_status_ended
                ContentStatus.IN_PROGRESS -> R.string.content_details_status_in_progress
                /* Movie status */
                ContentStatus.RUMORED -> R.string.content_details_status_rumored
                ContentStatus.PLANNED -> R.string.content_details_status_planned
                ContentStatus.IN_PRODUCTION -> R.string.content_details_status_in_production
                ContentStatus.POST_PRODUCTION -> R.string.content_details_status_post_production
                ContentStatus.RELEASED -> R.string.content_details_status_released
                ContentStatus.CANCELED -> R.string.content_details_status_canceled
                /* Other */
                ContentStatus.UNKNOWN -> R.string.content_details_status_unknown
            }
        )

    private fun getCustomContent(): CustomContent<ContentDetails>? {
        if (customContent == null) {
            customContent =
                intent?.extras?.getSerializable(EXTRA_CONTENT) as? CustomContent<ContentDetails>
        }
        return customContent
    }

    override fun getLayoutRes(): Int = R.layout.activity_custom_content_details
}