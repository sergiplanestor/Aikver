package revolhope.splanes.com.aikver.presentation.common.widget.contentpager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.presentation.common.loadUrl
import revolhope.splanes.com.core.domain.model.content.Content
import revolhope.splanes.com.core.domain.model.content.serie.Serie

class ContentPagerAdapter(
    private var items: List<Content>
) : RecyclerView.Adapter<ContentPagerAdapter.ViewHolder>() {

    val actualItemCount get() = items.size

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
            holder.punctuation.text = voteAverage.toString()
            holder.title.text = title
            holder.icon.setImageResource(
                if (this is Serie) {
                    R.drawable.ic_tv
                } else {
                    R.drawable.ic_film
                }
            )
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView by lazy { itemView.findViewById<ImageView>(R.id.image) }
        val title: TextView by lazy { itemView.findViewById<TextView>(R.id.title) }
        val icon: ImageView by lazy { itemView.findViewById<ImageView>(R.id.contentIcon) }
        val punctuation: TextView by lazy { itemView.findViewById<TextView>(R.id.punctuation) }
    }
}