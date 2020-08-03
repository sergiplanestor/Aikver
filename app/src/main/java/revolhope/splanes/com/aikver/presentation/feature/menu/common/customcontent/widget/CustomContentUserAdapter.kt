package revolhope.splanes.com.aikver.presentation.feature.menu.common.customcontent.widget

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.presentation.common.invisible
import revolhope.splanes.com.aikver.presentation.common.loadAvatar
import revolhope.splanes.com.aikver.presentation.common.visibility

class CustomContentUserAdapter(
    private val items: List<CustomContentUserUiModel>,
    private val currentUserId: String
) : RecyclerView.Adapter<CustomContentUserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_user_group_member_view,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        with(items[position]) {
            holder.image.loadAvatar(avatar)
            holder.userName.text = if (userId == currentUserId) {
                context.getString(R.string.you)
            } else {
                userName
            }
            holder.userName.setTextColor(
                ColorStateList.valueOf(
                    context.getColor(
                        if (userId == currentUserId) R.color.colorAccentDark else R.color.colorGray
                    )
                )
            )
            punctuation?.let {
                holder.punctuation.text = it.toInt().toString()
            } ?: holder.punctuation.invisible()
            holder.separator.visibility(position != items.lastIndex)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image by lazy<ImageView> { itemView.findViewById<ImageView>(R.id.avatar) }
        val userName by lazy<TextView> { itemView.findViewById<TextView>(R.id.userName) }
        val punctuation by lazy<TextView> { itemView.findViewById<TextView>(R.id.punctuation) }
        val separator by lazy<View> { itemView.findViewById<View>(R.id.separator) }
    }
}