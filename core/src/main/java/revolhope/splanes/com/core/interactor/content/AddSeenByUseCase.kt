package revolhope.splanes.com.core.interactor.content

import revolhope.splanes.com.core.data.repository.ContentRepository
import revolhope.splanes.com.core.domain.model.content.ContentDetails
import revolhope.splanes.com.core.domain.model.content.CustomContent
import revolhope.splanes.com.core.domain.model.user.User
import revolhope.splanes.com.core.domain.model.user.UserGroupMember
import revolhope.splanes.com.core.interactor.BaseUseCase

class AddSeenByUseCase(
    private val contentRepository: ContentRepository
) : BaseUseCase<AddSeenByUseCase.Request, List<UserGroupMember>>() {

    override suspend fun execute(req: Request): List<UserGroupMember>? =
        contentRepository.insertSeenBy(
            currentUser = req.currentUser,
            customContent = req.customContent
        )

    data class Request(
        val currentUser: User,
        val customContent: CustomContent<ContentDetails>
    )
}