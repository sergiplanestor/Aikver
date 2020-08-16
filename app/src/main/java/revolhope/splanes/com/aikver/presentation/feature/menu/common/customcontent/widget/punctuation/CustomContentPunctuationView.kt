package revolhope.splanes.com.aikver.presentation.feature.menu.common.customcontent.widget.punctuation

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.component_custom_content_punctuation_view.view.emptyStatePunctuation
import kotlinx.android.synthetic.main.component_custom_content_punctuation_view.view.punctuationAverage
import kotlinx.android.synthetic.main.component_custom_content_punctuation_view.view.punctuationButton
import kotlinx.android.synthetic.main.component_custom_content_punctuation_view.view.punctuationRecycler
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.presentation.common.invisible
import revolhope.splanes.com.aikver.presentation.common.toLowerCase
import revolhope.splanes.com.aikver.presentation.common.visibility
import revolhope.splanes.com.aikver.presentation.common.visible
import revolhope.splanes.com.aikver.presentation.feature.menu.common.customcontent.widget.CustomContentUserAdapter
import revolhope.splanes.com.aikver.presentation.feature.menu.common.customcontent.widget.CustomContentUserUiModel
import revolhope.splanes.com.core.domain.model.content.ContentDetails
import revolhope.splanes.com.core.domain.model.content.CustomContent
import revolhope.splanes.com.core.domain.model.content.serie.SerieDetails
import revolhope.splanes.com.core.domain.model.user.User
import revolhope.splanes.com.core.domain.model.user.UserGroupMember

class CustomContentPunctuationView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    attrDefStyle: Int = 0
) : LinearLayout(context, attributeSet, attrDefStyle) {

    private var currentUser: User? = null
    private var customContent: CustomContent<ContentDetails>? = null
    private var fm: FragmentManager? = null

    init {
        View.inflate(context, R.layout.component_custom_content_punctuation_view, this)
        layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )
    }

    fun setupPunctuation(
        currentUser: User,
        customContent: CustomContent<ContentDetails>,
        fm: FragmentManager
    ) {
        if (this.currentUser == null) this.currentUser = currentUser
        if (this.customContent == null) this.customContent = customContent
        if (this.fm == null) this.fm = fm

        if (customContent.punctuation.isEmpty()) {
            setupEmptyState(customContent.content is SerieDetails, currentUser, customContent, fm)
            punctuationButton.invisible()
            punctuationAverage.invisible()
            punctuationRecycler.invisible()
            emptyStatePunctuation.visible()
        } else {
            punctuationAverage.text =
                (customContent.punctuation.sumBy { (it.second * 10).toInt() } / (10 * customContent.punctuation.size)).toString()
            punctuationRecycler.layoutManager = LinearLayoutManager(context)
            punctuationRecycler.adapter =
                CustomContentUserAdapter(
                    items = customContent.punctuation.map {
                        CustomContentUserUiModel(
                            userId = it.first.userId,
                            avatar = it.first.avatar,
                            userName = it.first.username,
                            punctuation = it.second
                        )
                    },
                    currentUserId = currentUser.id
                )
            punctuationButton.visibility(isButtonVisible())
            punctuationButton.setOnClickListener {
                PunctuationBottomSheet(
                    currentUser = currentUser,
                    customContent = customContent,
                    onPunctuationUpdated = ::onPunctuationUpdated
                ).show(fm)
            }
        }
    }

    private fun setupEmptyState(
        isSerie: Boolean,
        currentUser: User,
        customContent: CustomContent<ContentDetails>,
        fm: FragmentManager
    ) {
        val contentText = context.getString(if (isSerie) R.string.serie else R.string.film)
        emptyStatePunctuation.setTitle(
            context.getString(
                R.string.content_no_scored,
                contentText.toLowerCase()
            )
        )
        emptyStatePunctuation.setActionText(context.getString(R.string.score_now))
        emptyStatePunctuation.setAction {
            PunctuationBottomSheet(
                currentUser = currentUser,
                customContent = customContent,
                onPunctuationUpdated = ::onPunctuationUpdated
            ).show(fm)
        }
        emptyStatePunctuation.setActionVisibility(isButtonVisible())
    }

    private fun isButtonVisible() =
        customContent?.seenBy?.any { it.userId == currentUser?.id }?.and(
            other = customContent?.punctuation?.any {
                it.first.userId == currentUser?.id
            }?.not() ?: false
        ) ?: false

    private fun onPunctuationUpdated(items: List<Pair<UserGroupMember, Float>>) {
        this.currentUser?.let {
            punctuationRecycler.adapter =
                CustomContentUserAdapter(
                    items = items.map { pair ->
                        CustomContentUserUiModel(
                            userId = pair.first.userId,
                            avatar = pair.first.avatar,
                            userName = pair.first.username,
                            punctuation = pair.second
                        )
                    },
                    currentUserId = it.id
                )
        }
    }

    fun onSerieSeenBy() {
        currentUser?.let { user ->
            customContent?.let { content ->
                fm?.let { fm ->
                    setupPunctuation(user, content, fm)
                }
            }
        }
    }
}