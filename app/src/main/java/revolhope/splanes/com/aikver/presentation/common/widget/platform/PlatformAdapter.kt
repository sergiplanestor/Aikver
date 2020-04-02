package revolhope.splanes.com.aikver.presentation.common.widget.platform

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.domain.Platform
import revolhope.splanes.com.aikver.framework.app.dpToPx

class PlatformAdapter(
    private val context: Context,
    private val dimension: Float,
    private val onPlatformClick: OnPlatformClick
) : RecyclerView.Adapter<PlatformAdapter.ViewHolder>() {

    private var currentSelected: Int = -1

    private val platforms: List<Platform> = listOf(
        Platform(R.drawable.platform_movistar, "Movistar+"),
        Platform(R.drawable.platform_hbo, "HBO"),
        Platform(R.drawable.platform_netflix, "Netflix"),
        Platform(R.drawable.platform_amazon, "Amazon Prime")
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(context)
            .inflate(R.layout.component_platform_viewer_holder, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return platforms.size
    }

    fun setItemSelected(itemName: String) {
        currentSelected = platforms.indexOfFirst { it.name.equals(itemName, true) }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = platforms[position]

        if (position != currentSelected) {
            holder.clearState()
        }
        holder.cardView.layoutParams =
            LinearLayout.LayoutParams(dimension.toInt(), dimension.toInt()).apply {
                setMargins(
                    holder.cardView.dpToPx(context, 5),
                    holder.cardView.dpToPx(context, 5),
                    holder.cardView.dpToPx(context, 5),
                    holder.cardView.dpToPx(context, 5)
                )
            }

        holder.imageView.setBackgroundResource(data.imageRes)
        holder.itemView.setOnClickListener {
            currentSelected = position
            holder.changeState()
            onPlatformClick.onPlatformClick(data)
            notifyDataSetChanged()
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardView: CardView = view.findViewById(R.id.platformCardView)
        val imageView: ImageView = view.findViewById(R.id.platformImageView)
        private var isSelected: Boolean = false
        private val container: LinearLayout = view.findViewById(R.id.platformContainer)

        fun changeState() {
            isSelected = !isSelected
            container.setBackgroundResource(
                if (isSelected) R.drawable.selected_item_bkg
                else 0
            )
        }

        fun clearState() {
            isSelected = true
            changeState()
        }
    }
}