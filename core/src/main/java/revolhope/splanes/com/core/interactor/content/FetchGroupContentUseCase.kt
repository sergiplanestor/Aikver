package revolhope.splanes.com.core.interactor.content

import revolhope.splanes.com.core.data.repository.ContentRepository
import revolhope.splanes.com.core.domain.model.content.ContentDetails
import revolhope.splanes.com.core.domain.model.content.CustomContent

class FetchGroupContentUseCase(private val contentRepository: ContentRepository) {
    suspend operator fun invoke(): List<CustomContent<ContentDetails>>? =
        contentRepository.fetchSelectedGroupContent()
}