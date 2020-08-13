package revolhope.splanes.com.aikver.presentation.feature.menu.common.customcontent.widget.comments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import revolhope.splanes.com.aikver.presentation.common.base.BaseViewModel
import revolhope.splanes.com.core.domain.model.content.ContentDetails
import revolhope.splanes.com.core.domain.model.content.CustomContent
import revolhope.splanes.com.core.domain.model.user.User
import revolhope.splanes.com.core.domain.model.user.UserGroupMember
import revolhope.splanes.com.core.interactor.content.AddCommentUseCase

class CommentsBottomSheetViewModel(
    private val addCommentUseCase: AddCommentUseCase
) : BaseViewModel() {

    val comments: LiveData<List<Pair<UserGroupMember, String>>> get() = _comments
    private val _comments = MutableLiveData<List<Pair<UserGroupMember, String>>>()

    fun addComment(
        currentUser: User,
        customContent: CustomContent<ContentDetails>,
        comment: String?
    ) {
        if (!comment.isNullOrBlank()) {
            launchAsync {
                _comments.postValue(
                    addCommentUseCase.invoke(
                        currentUser,
                        customContent,
                        comment
                    )
                )
            }
        } else {
            postError(null)
        }
    }

}