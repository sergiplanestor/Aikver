package revolhope.splanes.com.aikver.presentation.feature.menu.profile.avatar

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import revolhope.splanes.com.aikver.R

class UserAvatarColorAdapter(
    private val colors: List<String>,
    private var colorSelected: String,
    private val onClick: (color: String) -> Unit
) : RecyclerView.Adapter<UserAvatarColorAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_user_avatar_color, parent, false)
        )

    override fun getItemCount(): Int = colors.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val color = colors[position]

        holder.colorView.backgroundTintList = ColorStateList.valueOf(
            Color.parseColor("#$color")
        )

        holder.colorLayout.background =
            if (color == colorSelected) {
                ContextCompat.getDrawable(holder.itemView.context, R.drawable.selected_item_round)
            } else {
                null
            }

        holder.itemView.setOnClickListener {
            colorSelected = color
            onClick.invoke(color)
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val colorView: View = itemView.findViewById(R.id.colorView)
        val colorLayout: View = itemView.findViewById(R.id.colorLayout)
    }
}