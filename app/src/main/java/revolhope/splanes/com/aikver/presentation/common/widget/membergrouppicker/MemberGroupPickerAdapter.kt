package revolhope.splanes.com.aikver.presentation.common.widget.membergrouppicker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.presentation.common.loadAvatar
import revolhope.splanes.com.aikver.presentation.common.visibility
import revolhope.splanes.com.core.domain.model.user.UserGroupMember

class MemberGroupPickerAdapter(
    val members: MutableList<UserGroupMember>,
    val onMembersEmpty: () -> Unit
) : RecyclerView.Adapter<MemberGroupPickerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberGroupPickerAdapter.ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_member_group_picker,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = members.size

    override fun onBindViewHolder(holder: MemberGroupPickerAdapter.ViewHolder, position: Int) {
        with(members[position]) {
            holder.icon.loadAvatar(avatar)
            holder.name.text = username
            holder.delete.setOnClickListener {
                members.removeAt(position)
                notifyItemRemoved(position)
                if (members.isEmpty()) onMembersEmpty.invoke()
            }
            holder.separator.visibility(position != members.lastIndex)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon: ImageView by lazy { itemView.findViewById<ImageView>(R.id.memberIcon) }
        val name: TextView by lazy { itemView.findViewById<TextView>(R.id.memberNameTextView) }
        val delete: ImageView by lazy { itemView.findViewById<ImageView>(R.id.deleteButton) }
        val separator: View by lazy { itemView.findViewById<View>(R.id.separator) }
    }

}