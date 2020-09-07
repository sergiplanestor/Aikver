package revolhope.splanes.com.aikver.presentation.common.widget.contentpager

import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.presentation.common.loadUrl

class ContentPagerAdapter(
    private var items: List<ContentPagerUiModel>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<ContentPagerAdapter.ViewHolder>() {

    private val actualItemCount get() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.component_popular_pager_holder,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = if (items.isEmpty()) 0 else Int.MAX_VALUE

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(items[position % actualItemCount]) {
            holder.image.loadUrl(backdrop)
            holder.punctuation.text = voteAverage
            holder.title.text = title
            holder.title.setOnClickListener { displayTitle(it as TextView, holder.contentTitleRootView) }
            holder.icon.setImageResource(
                if (isSerie) R.drawable.ic_tv else R.drawable.ic_film
            )
            holder.itemView.setOnClickListener { onClick.invoke(id) }
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
        val image: ImageView by lazy { itemView.findViewById<ImageView>(R.id.image) }
        val title: TextView by lazy { itemView.findViewById<TextView>(R.id.title) }
        val icon: ImageView by lazy { itemView.findViewById<ImageView>(R.id.contentIcon) }
        val contentTitleRootView: ViewGroup by lazy { itemView.findViewById<ViewGroup>(R.id.contentTitleRootView) }
        val punctuation: TextView by lazy { itemView.findViewById<TextView>(R.id.punctuation) }
    }
}