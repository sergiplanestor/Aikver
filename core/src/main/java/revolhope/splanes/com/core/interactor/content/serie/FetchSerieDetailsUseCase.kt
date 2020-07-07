package revolhope.splanes.com.core.interactor.content.serie

import revolhope.splanes.com.core.data.repository.ContentRepository
import revolhope.splanes.com.core.domain.model.content.serie.SerieDetails

class FetchSerieDetailsUseCase(private val contentRepository: ContentRepository) {
    suspend operator fun invoke(serieId: Int): SerieDetails? =
        contentRepository.fetchSerieDetails(serieId)
}