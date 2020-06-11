package revolhope.splanes.com.core.interactor.content.serie

import revolhope.splanes.com.core.data.repository.ContentRepository

class InsertSerieUseCase(private val contentRepository: ContentRepository) {
    suspend operator fun invoke(model: Any): Boolean =
        true /*contentRepository.insertSerie(model)*/
}