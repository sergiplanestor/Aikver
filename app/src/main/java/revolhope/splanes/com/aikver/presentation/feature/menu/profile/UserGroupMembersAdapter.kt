package revolhope.splanes.com.aikver.presentation.feature.menu.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.presentation.common.loadAvatar
import revolhope.splanes.com.aikver.presentation.common.loadCircular
import revolhope.splanes.com.core.domain.model.UserGroupMember

class UserGroupMembersAdapter(
    private val currentUserId: String,
    private val mData: MutableList<UserGroupMember>
) : RecyclerView.Adapter<UserGroupMembersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_user_group_member,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        with(mData[position]) {
            holder.avatarImageView.loadAvatar(avatar)
            if (userId == currentUserId) {
                holder.usernameTextView.text = context.getString(R.string.you)
                holder.usernameTextView.setTextColor(context.getColor(R.color.colorPrimaryDark))
                holder.usernameTextView.typeface =
                    ResourcesCompat.getFont(context, R.font.comfortaa_bold)
            } else {
                holder.usernameTextView.text = username
                holder.usernameTextView.setTextColor(context.getColor(R.color.colorGray))
                holder.usernameTextView.typeface =
                    ResourcesCompat.getFont(context, R.font.comfortaa_regular)
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatarImageView: ImageView = itemView.findViewById(R.id.avatarImageView)
        val usernameTextView: TextView = itemView.findViewById(R.id.usernameTextView)
    }
}