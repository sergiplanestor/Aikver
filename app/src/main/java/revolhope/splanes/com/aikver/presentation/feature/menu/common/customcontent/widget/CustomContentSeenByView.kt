package revolhope.splanes.com.aikver.presentation.feature.menu.common.customcontent.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.component_custom_content_seen_by_view.view.emptyStateSeenBy
import kotlinx.android.synthetic.main.component_custom_content_seen_by_view.view.seenButton
import kotlinx.android.synthetic.main.component_custom_content_seen_by_view.view.seenByRecycler
import kotlinx.android.synthetic.main.component_custom_content_seen_by_view.view.seenByTitle
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.presentation.common.invisible
import revolhope.splanes.com.aikver.presentation.common.toLowerCase
import revolhope.splanes.com.aikver.presentation.common.visibility
import revolhope.splanes.com.aikver.presentation.common.visible
import revolhope.splanes.com.core.domain.model.content.ContentDetails
import revolhope.splanes.com.core.domain.model.content.CustomContent
import revolhope.splanes.com.core.domain.model.content.serie.SerieDetails
import revolhope.splanes.com.core.domain.model.user.User

class CustomContentSeenByView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    attrDefStyle: Int = 0
) : LinearLayout(context, attributeSet, attrDefStyle) {

    init {
        View.inflate(context, R.layout.component_custom_content_seen_by_view, this)
        layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )
    }

    fun setupSeenBy(currentUser: User, customContent: CustomContent<ContentDetails>) {
        val contentText =
            context.getString(if (customContent.content is SerieDetails) R.string.serie else R.string.film)
        seenByTitle.text =
            context.getString(R.string.users_that_have_seen_this, contentText.toLowerCase())

        if (customContent.seenBy.isEmpty()) {
            setupEmptyState(contentText)
            emptyStateSeenBy.visible()
            seenByRecycler.invisible()
            seenButton.invisible()
        } else {
            seenByRecycler.layoutManager = LinearLayoutManager(context)
            seenByRecycler.adapter = CustomContentUserAdapter(
                items = customContent.seenBy.map {
                    CustomContentUserUiModel(
                        userId = it.userId,
                        avatar = it.avatar,
                        userName = it.username
                    )
                },
                currentUserId = currentUser.id
            )
            seenButton.text = context.getString(R.string.seen_by, contentText)
            seenButton.visibility(customContent.seenBy.any { it.userId == currentUser.id }.not())
        }
    }

    private fun setupEmptyState(contentText: String) {
        emptyStateSeenBy.setTitle(
            context.getString(R.string.content_seen_by_nobody, contentText.toLowerCase())
        )
        emptyStateSeenBy.setActionText(context.getString(R.string.seen_by, contentText))
        emptyStateSeenBy.setAction {
            Toast.makeText(context, "TODO!", Toast.LENGTH_LONG).show()
        }
    }
}