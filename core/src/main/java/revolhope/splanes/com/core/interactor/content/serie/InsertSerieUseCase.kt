package revolhope.splanes.com.core.interactor.content.serie

import revolhope.splanes.com.core.data.repository.ContentRepository
import revolhope.splanes.com.core.domain.model.content.serie.Serie

class InsertSerieUseCase(private val contentRepository: ContentRepository) {
    suspend operator fun invoke(serie: Serie): Boolean = contentRepository.insertSerie(serie)
}