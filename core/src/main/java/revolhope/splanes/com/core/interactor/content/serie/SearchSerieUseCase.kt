package revolhope.splanes.com.core.interactor.content.serie

import revolhope.splanes.com.core.data.repository.ContentRepository
import revolhope.splanes.com.core.domain.model.content.serie.Serie
import revolhope.splanes.com.core.interactor.BaseUseCase

class SearchSerieUseCase(
    private val contentRepository: ContentRepository
) : BaseUseCase<SearchSerieUseCase.Request, List<Serie>>() {

    override suspend fun execute(req: Request): List<Serie>? =
        contentRepository.searchSeries(query = req.query)

    data class Request(val query: String)
}