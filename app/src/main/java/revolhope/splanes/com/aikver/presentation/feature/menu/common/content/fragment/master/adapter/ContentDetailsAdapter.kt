package revolhope.splanes.com.aikver.presentation.feature.menu.common.content.fragment.master.adapter

import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.presentation.common.invisible
import revolhope.splanes.com.aikver.presentation.common.justify
import revolhope.splanes.com.aikver.presentation.common.setAsLink
import revolhope.splanes.com.aikver.presentation.common.visible

class ContentDetailsAdapter(
    private val items: List<ContentDetailsUiModel>
) : RecyclerView.Adapter<ContentDetailsAdapter.ViewHolder>() {

    private companion object {
        private const val OVERVIEW_COMPACT_LINES = 5
        private const val OVERVIEW_COMPACT_MAX_LINES = 7
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.row_content_details_view,
                    parent,
                    false
                )
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleTextView.text = items[position].title
        holder.contentTextView.text = items[position].content
        holder.contentTextView.justify(items[position].isOverview)
        if (items[position].isLink) holder.contentTextView.setAsLink()
        if (items[position].isOverview) {
            holder.contentTextView.viewTreeObserver.addOnGlobalLayoutListener(
                object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        if (holder.contentTextView.lineCount > OVERVIEW_COMPACT_LINES) {
                            holder.contentTextView.maxLines = 7
                            holder.contentDecorator.visible()
                            holder.contentVisibilityButton.visible()
                            holder.contentVisibilityButton.setOnClickListener { swapOverviewState(holder) }
                        }
                        holder.contentTextView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                }
            )
        } else {
            holder.contentDecorator.invisible()
            holder.contentVisibilityButton.invisible()
        }
    }

    private fun swapOverviewState(holder: ViewHolder) {
        if (holder.contentTextView.maxLines == OVERVIEW_COMPACT_MAX_LINES) {
            holder.contentTextView.maxLines = Int.MAX_VALUE
            holder.contentDecorator.invisible(isGone = false)
            holder.contentVisibilityButton.text =
                holder.itemView.context.getString(R.string.content_details_overview_see_less)
        } else {
            holder.contentTextView.maxLines = OVERVIEW_COMPACT_MAX_LINES
            holder.contentDecorator.visible()
            holder.contentVisibilityButton.text =
                holder.itemView.context.getString(R.string.content_details_overview_see_more)
        }
        TransitionManager.beginDelayedTransition(holder.contentWrapLayout)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView by lazy { itemView.findViewById<TextView>(R.id.titleTextView) }
        val contentWrapLayout: ConstraintLayout by lazy { itemView.findViewById<ConstraintLayout>(R.id.contentWrapLayout) }
        val contentTextView: TextView by lazy { itemView.findViewById<TextView>(R.id.contentTextView) }
        val contentDecorator: View by lazy { itemView.findViewById<View>(R.id.contentDecorator) }
        val contentVisibilityButton: TextView by lazy { itemView.findViewById<TextView>(R.id.contentVisibilityButton) }
    }
}