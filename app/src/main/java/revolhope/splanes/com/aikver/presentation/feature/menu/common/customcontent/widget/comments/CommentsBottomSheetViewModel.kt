package revolhope.splanes.com.aikver.presentation.feature.menu.common.customcontent.widget.comments

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import revolhope.splanes.com.aikver.presentation.common.base.BaseViewModel
import revolhope.splanes.com.core.domain.model.content.ContentDetails
import revolhope.splanes.com.core.domain.model.content.CustomContent
import revolhope.splanes.com.core.domain.model.user.User
import revolhope.splanes.com.core.domain.model.user.UserGroupMember
import revolhope.splanes.com.core.interactor.content.AddCommentUseCase

class CommentsBottomSheetViewModel(
    context: Context,
    private val addCommentUseCase: AddCommentUseCase
) : BaseViewModel(context) {

    val comments: LiveData<List<Pair<UserGroupMember, String>>> get() = _comments
    private val _comments = MutableLiveData<List<Pair<UserGroupMember, String>>>()

    fun addComment(
        currentUser: User,
        customContent: CustomContent<ContentDetails>,
        comment: String?
    ) {
        if (!comment.isNullOrBlank()) {
            launchAsync {
                handleResponse(
                    state = addCommentUseCase.invoke(
                        AddCommentUseCase.Request(
                            currentUser = currentUser,
                            customContent = customContent,
                            comment = comment
                        )
                    )
                )?.let { _comments.postValue(it) }
            }
        }
    }
}