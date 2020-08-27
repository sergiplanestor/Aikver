package revolhope.splanes.com.core.interactor.content.serie

import revolhope.splanes.com.core.data.repository.ContentRepository
import revolhope.splanes.com.core.domain.model.content.serie.QueriedSeries
import revolhope.splanes.com.core.interactor.BaseUseCase

class FetchRelatedSeriesUseCase(
    private val contentRepository: ContentRepository
) : BaseUseCase<FetchRelatedSeriesUseCase.Request, QueriedSeries>() {

    override suspend fun execute(req: Request): QueriedSeries? =
        contentRepository.fetchRelatedSeries(serieId = req.serieId, page = req.page)

    data class Request(val serieId: Int, val page: Int)
}
