package revolhope.splanes.com.aikver.presentation.feature.menu.common.customcontent.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.component_custom_content_comments_view.view.commentsTitle
import kotlinx.android.synthetic.main.component_custom_content_comments_view.view.emptyStateComments
import kotlinx.android.synthetic.main.component_custom_content_comments_view.view.showCommentsButton
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.presentation.common.invisible
import revolhope.splanes.com.aikver.presentation.common.visible
import revolhope.splanes.com.core.domain.model.content.ContentDetails
import revolhope.splanes.com.core.domain.model.content.CustomContent

class CustomContentCommentsView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    attrDefStyle: Int = 0
) : LinearLayout(context, attributeSet, attrDefStyle) {

    init {
        View.inflate(context, R.layout.component_custom_content_comments_view, this)
        layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )
    }

    fun setupComments(customContent: CustomContent<ContentDetails>) {
        if (customContent.comments.isEmpty()) {
            commentsTitle.invisible()
            showCommentsButton.invisible()
            emptyStateComments.visible()
        } else {
            // TODO(¿show bottom sheet?)
            Toast.makeText(context, "TODO!", Toast.LENGTH_LONG).show()
        }
    }
}