package revolhope.splanes.com.aikver.presentation.searchserie
/*

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import revolhope.splanes.com.aikver.R
import bemobile.splanes.com.core.domain.Serie
import revolhope.splanes.com.aikver.presentation.common.widget.ScoreView
import com.bumptech.glide.Glide

class SerieResultAdapter(
    private val dataset: List<bemobile.splanes.com.core.domain.Serie>,
    private val onClickListener: (bemobile.splanes.com.core.domain.Serie) -> Unit
) : RecyclerView.Adapter<SerieResultAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_search_result, parent, false)
        )

    override fun getItemCount(): Int = dataset.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = dataset[position]

        holder.title.text = item.title
        if (item.platform != null) holder.platformImage.setImageResource(item.platform.imageRes)
        if (item.category != null) holder.category.text = item.category
        with(holder.scoreView) {
            updateScore(item.score)
            isEditable(false)
            setDark()
            hideNumeric()
        }
        Glide.with(holder.itemView.context).load(item.imageUrl).into(holder.thumbnail)
        holder.itemView.setOnClickListener { onClickListener.invoke(item) }
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumbnail: ImageView = itemView.findViewById(R.id.thumbnailImageView)
        val title: TextView = itemView.findViewById(R.id.titleTextView)
        val category: TextView = itemView.findViewById(R.id.categoryTextView)
        val scoreView: ScoreView = itemView.findViewById(R.id.scoreView)
        val platformImage: ImageView = itemView.findViewById(R.id.platformImageView)
    }
}*/
