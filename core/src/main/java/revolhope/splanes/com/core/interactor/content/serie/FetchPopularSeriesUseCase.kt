package revolhope.splanes.com.core.interactor.content.serie

import revolhope.splanes.com.core.data.repository.ContentRepository
import revolhope.splanes.com.core.domain.model.content.serie.QueriedSeries
import revolhope.splanes.com.core.interactor.BaseUseCase

class FetchPopularSeriesUseCase(
    private val contentRepository: ContentRepository
) : BaseUseCase<FetchPopularSeriesUseCase.Request, QueriedSeries>() {

    override suspend fun execute(req: Request): QueriedSeries? =
        contentRepository.fetchPopularSeries(page = req.page)

    data class Request(val page: Int)
}