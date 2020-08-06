package revolhope.splanes.com.aikver.presentation.feature.menu.common.customcontent.widget.comments

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_comments_bottom_sheet.commentsRecycler
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.presentation.common.base.BaseBottomSheet
import revolhope.splanes.com.core.domain.model.user.User
import revolhope.splanes.com.core.domain.model.user.UserGroupMember

class CommentsBottomSheet(
    private val items: List<Pair<UserGroupMember, String>>,
    private val currentUser: User
) : BaseBottomSheet() {

    override fun getLayoutResource(): Int = R.layout.fragment_comments_bottom_sheet

    override fun bindView(view: View) {
        commentsRecycler.layoutManager = LinearLayoutManager(requireContext())
        commentsRecycler.adapter = CommentsAdapter(
            items = items.map {
                CommentUiModel(
                    avatar = it.first.avatar,
                    name = it.first.username,
                    comment = it.second,
                    isCurrentUser = it.first.userId == currentUser.id
                )
            }
        )
    }
}