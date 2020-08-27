package revolhope.splanes.com.core.interactor.content

import revolhope.splanes.com.core.data.repository.ContentRepository
import revolhope.splanes.com.core.domain.model.content.ContentDetails
import revolhope.splanes.com.core.domain.model.content.CustomContent
import revolhope.splanes.com.core.interactor.BaseUseCase

class FetchGroupContentUseCase(
    private val contentRepository: ContentRepository
) : BaseUseCase<FetchGroupContentUseCase.Request, List<CustomContent<ContentDetails>>>() {

    override suspend fun execute(req: Request): List<CustomContent<ContentDetails>>? =
        contentRepository.fetchSelectedGroupContent()

    object Request
}