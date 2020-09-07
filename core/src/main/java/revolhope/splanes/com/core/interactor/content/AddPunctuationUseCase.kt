package revolhope.splanes.com.core.interactor.content

import revolhope.splanes.com.core.data.repository.ContentRepository
import revolhope.splanes.com.core.domain.model.content.ContentDetails
import revolhope.splanes.com.core.domain.model.content.CustomContent
import revolhope.splanes.com.core.domain.model.user.User
import revolhope.splanes.com.core.domain.model.user.UserGroupMember
import revolhope.splanes.com.core.interactor.BaseUseCase

class AddPunctuationUseCase(
    private val contentRepository: ContentRepository
) : BaseUseCase<AddPunctuationUseCase.Request, List<Pair<UserGroupMember, Float>>>() {

    override suspend fun execute(req: Request): List<Pair<UserGroupMember, Float>>?  =
        contentRepository.insertPunctuation(
            currentUser = req.currentUser,
            customContent = req.customContent,
            punctuation = req.punctuation
        )

    data class Request(
        val currentUser: User,
        val customContent: CustomContent<ContentDetails>,
        val punctuation: Int
    )
}