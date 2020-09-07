package revolhope.splanes.com.aikver.presentation.common.widget.membergrouppicker.bottomsheet

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

class MemberGroupPickerBottomSheetAdapter(
    var members: List<MemberGroupPickerBottomSheetUiModel>
) : RecyclerView.Adapter<MemberGroupPickerBottomSheetAdapter.ViewHolder>() {

    val pickedMembers: List<UserGroupMember>
        get() = members.filter { it.isPicked }.map { it.member }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_member_group_picker_bottom_sheet,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = members.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(members[position]) {
            holder.icon.loadAvatar(member.avatar)
            holder.name.text = member.username
            holder.pickedImage.visibility(isPicked)
            holder.itemView.setOnClickListener {
                isPicked = isPicked.not()
                notifyItemChanged(position)
            }
            holder.separator.visibility(position != members.lastIndex)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon: ImageView by lazy { itemView.findViewById<ImageView>(R.id.memberIcon) }
        val name: TextView by lazy { itemView.findViewById<TextView>(R.id.memberNameTextView) }
        val pickedImage: ImageView by lazy { itemView.findViewById<ImageView>(R.id.memberPickedImageView) }
        val separator: View by lazy { itemView.findViewById<View>(R.id.separator) }
    }
}