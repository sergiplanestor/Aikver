package revolhope.splanes.com.core.interactor.content

import revolhope.splanes.com.core.data.repository.ContentRepository
import revolhope.splanes.com.core.domain.model.content.serie.Serie

class SearchSerieUseCase(private val contentRepository: ContentRepository) {
    suspend operator fun invoke(query: String): List<Serie>? = contentRepository.searchSeries(query)
}