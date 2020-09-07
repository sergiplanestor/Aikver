package revolhope.splanes.com.aikver.presentation.feature.menu.common.customcontent.widget.comments

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_comments_bottom_sheet.commentEditText
import kotlinx.android.synthetic.main.fragment_comments_bottom_sheet.commentsRecycler
import kotlinx.android.synthetic.main.fragment_comments_bottom_sheet.sendCommentButton
import org.koin.android.viewmodel.ext.android.viewModel
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.framework.app.observe
import revolhope.splanes.com.aikver.presentation.common.base.BaseBottomSheet
import revolhope.splanes.com.core.domain.model.content.ContentDetails
import revolhope.splanes.com.core.domain.model.content.CustomContent
import revolhope.splanes.com.core.domain.model.user.User

class CommentsBottomSheet(
    private val customContent: CustomContent<ContentDetails>,
    private val currentUser: User
) : BaseBottomSheet() {

    private val viewModel: CommentsBottomSheetViewModel by viewModel()

    override fun getLayoutResource(): Int = R.layout.fragment_comments_bottom_sheet

    override fun bindView(view: View) {
        bindObservers()
        commentsRecycler.layoutManager = LinearLayoutManager(requireContext())
        commentsRecycler.adapter = CommentsAdapter(
            items = customContent.comments.map {
                CommentUiModel(
                    avatar = it.first.avatar,
                    name = it.first.username,
                    comment = it.second,
                    isCurrentUser = it.first.userId == currentUser.id
                )
            }
        )
        sendCommentButton.setOnClickListener {
            viewModel.addComment(
                currentUser,
                customContent,
                commentEditText.text?.toString()
            )
        }
    }

    private fun bindObservers() {
        observe(viewModel.comments) {
            commentEditText.text?.clear()
            (commentsRecycler.adapter as? CommentsAdapter)?.onNewItems(
                it.map { pair ->
                    CommentUiModel(
                        avatar = pair.first.avatar,
                        name = pair.first.username,
                        comment = pair.second,
                        isCurrentUser = pair.first.userId == currentUser.id
                    )
                }
            )
        }
    }
}