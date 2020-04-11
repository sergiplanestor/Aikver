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
    private val onClick: (color: String) -> Unit
) : RecyclerView.Adapter<UserAvatarColorAdapter.ViewHolder>() {

    private val colors: List<String> = listOf(
        "FFF000", // Yellow
        "B2FF59", // Green
        "FFA726", // Orange
        "26C6DA", // Blue
        "4DB6AC", // Turquoise
        "7E57C2", // Purple
        "E53935", // Red
        "F06292", // Pink
        "8D6E63", // Brown
        "78909C"  // Gray
    )
    private var colorSelected: String = "FFF000"

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