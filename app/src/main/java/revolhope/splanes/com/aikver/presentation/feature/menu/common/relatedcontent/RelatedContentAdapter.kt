package revolhope.splanes.com.aikver.presentation.feature.menu.common.relatedcontent

import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.presentation.common.loadUrl
import revolhope.splanes.com.core.domain.model.content.Content

class RelatedContentAdapter(
    private val items: MutableList<Content>,
    private val onItemClick: (Content) -> Unit
) : RecyclerView.Adapter<RelatedContentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_content_search_result,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(items[position]) {
            holder.image.scaleType = ImageView.ScaleType.CENTER_CROP
            holder.image.loadUrl(thumbnail)
            holder.image.setOnClickListener { onItemClick.invoke(this) }
            holder.title.text = title
            holder.title.setOnClickListener { displayTitle(it as TextView, holder.titleRoot) }
        }
    }

    fun addItems(items: List<Content>) {
        this.items.addAll(this.items.lastIndex, items)
        notifyItemRangeInserted(this.items.lastIndex, items.size)
    }

    private fun displayTitle(view: TextView, root: ViewGroup) {
        with(view) {
            maxLines = if (maxLines == 1) 5 else 1
            invalidate()
            TransitionManager.beginDelayedTransition(root)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.contentImageView)
        val titleRoot: FrameLayout = itemView.findViewById(R.id.contentTitleRootView)
        val title: TextView = itemView.findViewById(R.id.contentTextView)
    }
}