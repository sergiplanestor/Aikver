package revolhope.splanes.com.aikver.presentation.feature.menu.dashboard

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
import revolhope.splanes.com.core.domain.model.content.ContentDetails
import revolhope.splanes.com.core.domain.model.content.CustomContent
import revolhope.splanes.com.core.domain.model.content.serie.SerieDetails

class GroupContentAdapter(
     private val items: List<CustomContent<ContentDetails>>,
     private val onItemClick: (CustomContent<ContentDetails>) -> Unit
) : RecyclerView.Adapter<GroupContentAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_group_content_view,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(items[position]) {
            holder.contentImageView.loadUrl(content.thumbnail)
            holder.itemView.setOnClickListener { onItemClick.invoke(this) }
            holder.contentTextView.text = content.title
            holder.contentTextView.setOnClickListener { displayTitle(it as TextView, holder.contentTitleRootView) }
            holder.contentIndicator.setImageResource(
                when(content) {
                    is SerieDetails -> R.drawable.ic_tv
                    else /* MovieDetails */ -> R.drawable.ic_film
                }
            )
        }
    }

    private fun displayTitle(view: TextView, root: ViewGroup) {
        with(view) {
            maxLines = if (maxLines == 1) 5 else 1
            invalidate()
            TransitionManager.beginDelayedTransition(root)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val contentImageView: ImageView by lazy { itemView.findViewById<ImageView>(R.id.contentImageView) }
        val contentIndicator: ImageView by lazy { itemView.findViewById<ImageView>(R.id.contentIndicator) }
        val contentTitleRootView: FrameLayout by lazy { itemView.findViewById<FrameLayout>(R.id.contentTitleRootView) }
        val contentTextView: TextView by lazy { itemView.findViewById<TextView>(R.id.contentTextView) }
    }
}