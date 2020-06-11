package revolhope.splanes.com.aikver.presentation.common.widget.networkselector

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.core.domain.model.content.Network

class NetworkAdapter : RecyclerView.Adapter<NetworkAdapter.ViewHolder>() {

    private var selected: Network = Network.UNKNOWN
    private val items: List<Pair<String, Int>> = mutableListOf(
        Network.NETFLIX.toString() to R.drawable.network_netflix,
        Network.HBO.toString() to R.drawable.network_hbo,
        Network.AMAZON_PRIME.toString() to R.drawable.network_amazon,
        Network.MOVISTAR.toString() to R.drawable.network_movistar
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.component_network_selector_holder, parent, false)
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.setImageResource(items[position].second)
        holder.container.background = if (selected == Network.fromName(items[position].first)) {
            holder.itemView.resources.getDrawable(R.drawable.selected_item_bkg, null)
        } else {
            null
        }
        holder.itemView.setOnClickListener {
            selected =
                if (selected != Network.fromName(items[position].first)) {
                    Network.fromName(items[position].first)
                } else {
                    Network.UNKNOWN
                }
            notifyDataSetChanged()
        }
    }

    fun getSelected() : Network = selected

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val container: LinearLayout = itemView.findViewById(R.id.networkContainer)
        val image: ImageView = itemView.findViewById(R.id.networkImageView)
    }
}