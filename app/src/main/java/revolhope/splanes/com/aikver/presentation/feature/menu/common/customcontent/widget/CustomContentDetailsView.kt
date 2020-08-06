package revolhope.splanes.com.aikver.presentation.feature.menu.common.customcontent.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.WindowContentFrameStats
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.component_custom_content_details_view.view.addedByAvatar
import kotlinx.android.synthetic.main.component_custom_content_details_view.view.addedByName
import kotlinx.android.synthetic.main.component_custom_content_details_view.view.addedByTitle
import kotlinx.android.synthetic.main.component_custom_content_details_view.view.commentsView
import kotlinx.android.synthetic.main.component_custom_content_details_view.view.punctuationView
import kotlinx.android.synthetic.main.component_custom_content_details_view.view.seenByView
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.presentation.common.loadAvatar
import revolhope.splanes.com.core.domain.model.content.ContentDetails
import revolhope.splanes.com.core.domain.model.content.CustomContent
import revolhope.splanes.com.core.domain.model.content.serie.SerieDetails
import revolhope.splanes.com.core.domain.model.user.User

class CustomContentDetailsView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    attrDefStyle: Int = 0
) : ConstraintLayout(context, attributeSet, attrDefStyle) {

    init {
        View.inflate(context, R.layout.component_custom_content_details_view, this)
        layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )
    }

    fun initialize(
        currentUser: User,
        customContent: CustomContent<ContentDetails>,
        fragmentManager: FragmentManager
    ) {
        seenByView.setupSeenBy(currentUser, customContent)
        punctuationView.setupPunctuation(currentUser, customContent)
        commentsView.setupComments(currentUser, customContent, fragmentManager)
        setupAddedBy(customContent)
    }

    private fun setupAddedBy(customContent: CustomContent<ContentDetails>) {
        addedByName.text = customContent.userAdded.username
        addedByAvatar.loadAvatar(customContent.userAdded.avatar)
        addedByTitle.text = context.getString(
            R.string.content_added_by,
            context.getString(
                if (customContent.content is SerieDetails) R.string.serie else R.string.film
            )
        )
    }
}