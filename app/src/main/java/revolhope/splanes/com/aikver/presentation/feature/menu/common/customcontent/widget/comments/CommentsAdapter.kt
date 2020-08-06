package revolhope.splanes.com.aikver.presentation.feature.menu.common.customcontent.widget.comments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.presentation.common.justify
import revolhope.splanes.com.aikver.presentation.common.loadAvatar

class CommentsAdapter(
    private val items: List<CommentUiModel>
) : RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_comment_view,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(items[position]) {
            val context = holder.itemView.context
            holder.avatar.loadAvatar(avatar)
            holder.name.text = if (isCurrentUser) context.getString(R.string.you) else name
            holder.name.setTextColor(
                context.getColor(if (isCurrentUser) R.color.colorAccentDark else R.color.colorGray)
            )
            holder.comment.text = comment
            holder.comment.justify()
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatar: ImageView by lazy<ImageView> { itemView.findViewById<ImageView>(R.id.avatar) }
        val name: TextView by lazy<TextView> { itemView.findViewById<TextView>(R.id.userName) }
        val comment: TextView by lazy<TextView> { itemView.findViewById<TextView>(R.id.comment) }
    }
}