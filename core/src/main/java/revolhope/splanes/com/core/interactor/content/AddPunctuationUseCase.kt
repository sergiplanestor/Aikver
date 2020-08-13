package revolhope.splanes.com.core.interactor.content

import revolhope.splanes.com.core.data.repository.ContentRepository
import revolhope.splanes.com.core.domain.model.content.ContentDetails
import revolhope.splanes.com.core.domain.model.content.CustomContent
import revolhope.splanes.com.core.domain.model.user.User
import revolhope.splanes.com.core.domain.model.user.UserGroupMember

class AddPunctuationUseCase(private val contentRepository: ContentRepository) {
    suspend operator fun invoke(
        currentUser: User,
        customContent: CustomContent<ContentDetails>,
        punctuation: Int
    ): List<Pair<UserGroupMember, Float>> =
        contentRepository.insertPunctuation(currentUser, customContent, punctuation)
}