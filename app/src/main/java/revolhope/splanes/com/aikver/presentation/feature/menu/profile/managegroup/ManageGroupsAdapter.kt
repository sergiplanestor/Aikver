package revolhope.splanes.com.aikver.presentation.feature.menu.profile.managegroup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.presentation.common.loadGroupIcon
import revolhope.splanes.com.aikver.presentation.common.visibility
import revolhope.splanes.com.core.domain.model.User
import revolhope.splanes.com.core.domain.model.UserGroup

class ManageGroupsAdapter(
    private val user: User,
    private val items: List<UserGroup>,
    private val onAddGroupClick: () -> Unit,
    private val onItemLongClick: (UserGroup) -> Unit,
    private val onItemClick: (UserGroup) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class ViewHolderType(val value: Int) {
        HEADER(0),
        CONTENT(1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        if (viewType == ViewHolderType.HEADER.value) {
            HeaderViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.row_user_group_header,
                    parent,
                    false
                )
            )
        } else /* viewType == ViewHolderType.CONTENT.value */ {
            ContentViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.row_user_group_content,
                    parent,
                    false
                )
            )
        }

    override fun getItemViewType(position: Int): Int =
        if (position == 0) {
            ViewHolderType.HEADER.value
        } else {
            ViewHolderType.CONTENT.value
        }

    override fun getItemCount(): Int = items.size + 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> bindHeaderViewHolder(holder)
            is ContentViewHolder -> bindContentViewHolder(holder, items[position - 1])
        }
    }

    private fun bindHeaderViewHolder(holder: HeaderViewHolder) =
        holder.addButton.setOnClickListener { onAddGroupClick.invoke() }

    private fun bindContentViewHolder(holder: ContentViewHolder, item: UserGroup) {
        holder.iconImageView.loadGroupIcon(item.name, item.userGroupAdmin.avatar.color)
        holder.titleTextView.text = item.name
        holder.countTextView.text =
            holder.itemView.context.getString(R.string.member_num, item.members.size)
        holder.layout.background =
            if (user.selectedUserGroup?.id == item.id) {
                ContextCompat.getDrawable(holder.itemView.context, R.drawable.selected_item_bkg)
            } else {
                null
            }
        holder.separator.visibility(user.selectedUserGroup?.id != item.id, isGone = false)
        holder.adminIndicator.visibility(user.id == item.userGroupAdmin.userId)

        holder.itemView.setOnClickListener { onItemClick.invoke(item) }
        holder.itemView.setOnLongClickListener {
            if (user.selectedUserGroup?.id != item.id) {
                onItemLongClick.invoke(item)
                true
            } else {
                false
            }
        }
    }

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val addButton: MaterialButton = itemView.findViewById(R.id.addGroupButton)
    }

    inner class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout: ConstraintLayout = itemView.findViewById(R.id.groupItemLayout)
        val iconImageView: ImageView = itemView.findViewById(R.id.groupImageView)
        val titleTextView: TextView = itemView.findViewById(R.id.groupTitleTextView)
        val countTextView: TextView = itemView.findViewById(R.id.groupMemberCountTextView)
        val adminIndicator: TextView = itemView.findViewById(R.id.groupAdminTextView)
        val separator: View = itemView.findViewById(R.id.separator)
    }
}