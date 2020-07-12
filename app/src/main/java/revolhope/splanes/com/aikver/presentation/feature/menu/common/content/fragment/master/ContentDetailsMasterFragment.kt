package revolhope.splanes.com.aikver.presentation.feature.menu.common.content.fragment.master

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_content_details_master.collapsingToolbarImageView
import kotlinx.android.synthetic.main.fragment_content_details_master.companyImage
import kotlinx.android.synthetic.main.fragment_content_details_master.contentRecycler
import kotlinx.android.synthetic.main.fragment_content_details_master.extraInfo1TextView
import kotlinx.android.synthetic.main.fragment_content_details_master.extraInfo2TextView
import kotlinx.android.synthetic.main.fragment_content_details_master.relatedContentGroup
import kotlinx.android.synthetic.main.fragment_content_details_master.relatedContentRecycler
import kotlinx.android.synthetic.main.fragment_content_details_master.relatedContentTextView
import kotlinx.android.synthetic.main.fragment_content_details_master.statusTextView
import kotlinx.android.synthetic.main.fragment_content_details_master.toolbar
import kotlinx.android.synthetic.main.fragment_content_details_master.voteAverageTextView
import kotlinx.android.synthetic.main.fragment_content_details_master.voteCardView
import org.koin.android.viewmodel.ext.android.viewModel
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.framework.app.observe
import revolhope.splanes.com.aikver.presentation.common.base.BaseActivity
import revolhope.splanes.com.aikver.presentation.common.base.BaseFragment
import revolhope.splanes.com.aikver.presentation.common.invisible
import revolhope.splanes.com.aikver.presentation.common.loadUrl
import revolhope.splanes.com.aikver.presentation.common.popupError
import revolhope.splanes.com.aikver.presentation.common.toCustomString
import revolhope.splanes.com.aikver.presentation.common.visible
import revolhope.splanes.com.aikver.presentation.feature.menu.common.content.ContentDetailsActivity
import revolhope.splanes.com.aikver.presentation.feature.menu.common.content.fragment.master.adapter.ContentDetailsAdapter
import revolhope.splanes.com.aikver.presentation.feature.menu.common.content.fragment.master.adapter.ContentDetailsUiModel
import revolhope.splanes.com.aikver.presentation.feature.menu.common.content.fragment.master.relatedcontent.RelatedContentAdapter
import revolhope.splanes.com.aikver.presentation.feature.menu.common.content.fragment.master.relatedcontent.RelatedContentLayoutManager
import revolhope.splanes.com.core.domain.model.content.Content
import revolhope.splanes.com.core.domain.model.content.ContentDetails
import revolhope.splanes.com.core.domain.model.content.ContentStatus
import revolhope.splanes.com.core.domain.model.content.QueriedContent
import revolhope.splanes.com.core.domain.model.content.movie.MovieDetails
import revolhope.splanes.com.core.domain.model.content.movie.QueriedMovies
import revolhope.splanes.com.core.domain.model.content.serie.QueriedSeries
import revolhope.splanes.com.core.domain.model.content.serie.Serie
import revolhope.splanes.com.core.domain.model.content.serie.SerieDetails
import java.util.*

class ContentDetailsMasterFragment : BaseFragment() {

    private val viewModel: ContentDetailsMasterViewModel by viewModel()

    override fun initViews() {
        super.initViews()
        collapsingToolbarImageView.loadUrl(
            getContent()?.backdrop.let {
                if (it.isNullOrBlank()) getContent()?.thumbnail ?: "" else it
            }
        )
        (activity as? ContentDetailsActivity)?.setupToolbar(toolbar)
    }

    override fun initObservers() {
        observe(viewModel.loaderState) { if (it) showLoader() else hideLoader() }
        observe(viewModel.contentDetails) {
            it?.let(::bindViews) ?: popupError(
                context = requireContext(),
                fm = childFragmentManager,
                action = (activity as ContentDetailsActivity)::onBackPressed
            )
        }
        observe(viewModel.contentRelated) {
            if (it == null || it.results.isEmpty()) {
                relatedContentGroup.invisible()
            } else {
                bindRelatedContent(it)
            }
        }
    }

    override fun loadData() {
        getContent()?.let {
            viewModel.fetchDetails(it.id, it is Serie)
            viewModel.fetchContentRelated(it.id, it is Serie)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindViews(details: ContentDetails) {

        /* Subtitle */
        (activity as? ContentDetailsActivity)?.supportActionBar?.subtitle =
            when (details) {
                is SerieDetails -> details.firstAirDate.split("-")[0]
                is MovieDetails -> details.tagLine
                else -> null
            }

        /* Toolbar extra info */
        bindBottomToolbarInfo(details)

        /* Recycler content info */
        contentRecycler.layoutManager = LinearLayoutManager(requireContext())
        contentRecycler.adapter = ContentDetailsAdapter(buildAdapterData(details))

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

    private fun buildAdapterData(details: ContentDetails): List<ContentDetailsUiModel> {
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
            relatedContentRecycler.layoutManager = RelatedContentLayoutManager(requireContext()) {
                getContent()?.let {
                    viewModel.fetchContentRelated(it.id, it is Serie, relatedContent.page + 1)
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
        ContentDetailsActivity.start(requireActivity() as? BaseActivity, content)
        requireActivity().finish() // TODO: Check it: deberia pasar algun flag para poner back o no?
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

    private fun getContent(): Content? = (activity as? ContentDetailsActivity)?.getContent()

    override fun getLayoutResource(): Int = R.layout.fragment_content_details_master
}