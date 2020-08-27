package revolhope.splanes.com.core.interactor.content

import revolhope.splanes.com.core.data.repository.ContentRepository
import revolhope.splanes.com.core.domain.model.content.ContentDetails
import revolhope.splanes.com.core.domain.model.content.CustomContent
import revolhope.splanes.com.core.domain.model.user.User
import revolhope.splanes.com.core.domain.model.user.UserGroupMember
import revolhope.splanes.com.core.interactor.BaseUseCase

class AddCommentUseCase(
    private val contentRepository: ContentRepository
) : BaseUseCase<AddCommentUseCase.Request, List<Pair<UserGroupMember, String>>>() {

    override suspend fun execute(req: Request): List<Pair<UserGroupMember, String>>? =
        contentRepository.insertComment(
            currentUser = req.currentUser,
            customContent = req.customContent,
            comment = req.comment
        )

    data class Request(
        val currentUser: User,
        val customContent: CustomContent<ContentDetails>,
        val comment: String
    )
}