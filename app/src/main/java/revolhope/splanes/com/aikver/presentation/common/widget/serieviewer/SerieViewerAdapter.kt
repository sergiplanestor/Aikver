package revolhope.splanes.com.aikver.presentation.common.widget.serieviewer
/*

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bemobile.splanes.com.core.domain.Serie

class SerieViewerAdapter(
    private val mItems: MutableList<bemobile.splanes.com.core.domain.Serie>,
    private val listener: (serie: bemobile.splanes.com.core.domain.Serie) -> Unit
): RecyclerView.Adapter<SerieViewerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder(SerieView(parent.context))

    override fun getItemCount(): Int = mItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView as SerieView) {
            initialize(
                url = mItems[position].imageUrl,
                title = mItems[position].title,
                score = mItems[position].score
            )
            setOnClickListener { listener.invoke(mItems[position]) }
        }
    }

    fun updateItems(mItems: List<bemobile.splanes.com.core.domain.Serie>) {
        this.mItems.clear()
        this.mItems.addAll(mItems)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view)
}*/
