package revolhope.splanes.com.core.interactor.content.serie

import revolhope.splanes.com.core.data.repository.ContentRepository
import revolhope.splanes.com.core.domain.model.content.serie.QueriedSeries

class FetchPopularSeriesUseCase(private val contentRepository: ContentRepository) {
    suspend operator fun invoke(page: Int): QueriedSeries? =
        contentRepository.fetchPopularSeries(page)
}