package revolhope.splanes.com.aikver.presentation.feature.menu.profile.avatar.typeselector

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.presentation.common.loadAvatar
import revolhope.splanes.com.aikver.presentation.common.visibility
import revolhope.splanes.com.aikver.presentation.common.visible

class TypeSelectorAdapter(
    private val items: List<TypeSelectorUiModel>
) : RecyclerView.Adapter<TypeSelectorAdapter.ViewHolder>() {

    val selectedItem: TypeSelectorUiModel? get() = items.find { it.isChecked }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_avatar_type_selector,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(items[position]) {
            holder.preview.loadAvatar(avatar)
            holder.title.text = title
            if (isChecked) {
                holder.checked.setImageResource(R.drawable.ic_check)
                holder.checked.visible()
            } else {
                holder.checked.visibility(false, isGone = false)
            }
            holder.checked.imageTintList = ColorStateList.valueOf(
                holder.itemView.context.getColor(
                    android.R.color.holo_green_dark
                )
            )
            holder.separator.visibility(show = position != items.lastIndex)
            holder.itemView.setOnClickListener {
                isChecked = isChecked.not()
                items.forEach { if (it.title != title) it.isChecked = false }
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val preview: ImageView by lazy { itemView.findViewById<ImageView>(R.id.typePreview) }
        val title: TextView by lazy { itemView.findViewById<TextView>(R.id.typeName) }
        val checked: ImageView by lazy { itemView.findViewById<ImageView>(R.id.checkImageView) }
        val separator: View by lazy { itemView.findViewById<View>(R.id.separator) }
    }
}