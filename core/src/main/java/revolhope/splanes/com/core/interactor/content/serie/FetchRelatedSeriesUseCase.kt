package revolhope.splanes.com.core.interactor.content.serie

import revolhope.splanes.com.core.data.repository.ContentRepository
import revolhope.splanes.com.core.domain.model.content.serie.QueriedSeries

class FetchRelatedSeriesUseCase(private val contentRepository: ContentRepository) {
    suspend operator fun invoke(serieId: Int, page: Int): QueriedSeries? =
        contentRepository.fetchRelatedSeries(serieId, page)
}
