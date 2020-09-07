package revolhope.splanes.com.aikver.presentation.feature.menu.profile.managegroup.details

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
import revolhope.splanes.com.aikver.presentation.common.visible
import revolhope.splanes.com.core.domain.model.user.UserGroupMember

class MembersAdapter(
    private var items: List<UserGroupMember>,
    private val isAdmin: Boolean,
    private val userId: String,
    private val onDelete: (UserGroupMember) -> Unit
) : RecyclerView.Adapter<MembersAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_group_member, parent, false)
        )


    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(items[position]) {
            holder.icon.loadAvatar(avatar)
            holder.name.text = username
            if (isAdmin && userId != this@MembersAdapter.userId) {
                holder.deleteButton.visible()
                holder.deleteButton.setOnClickListener {
                    onDelete.invoke(this)
                }
            } else {
                holder.deleteButton.invisible()
            }
            holder.separator.visibility(show = position < items.size - 1)
        }
    }

    fun updateItems(items: List<UserGroupMember>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon: ImageView = itemView.findViewById(R.id.memberIcon)
        val name: TextView = itemView.findViewById(R.id.memberNameTextView)
        val deleteButton: ImageView = itemView.findViewById(R.id.memberDeleteButton)
        val separator: View = itemView.findViewById(R.id.separator)
    }
}