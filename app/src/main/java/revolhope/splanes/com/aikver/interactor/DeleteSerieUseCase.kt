package revolhope.splanes.com.aikver.interactor

import revolhope.splanes.com.aikver.data.serie.SerieRepository
import revolhope.splanes.com.aikver.domain.Serie

class DeleteSerieUseCase(private val serieRepository: SerieRepository) {
    suspend operator fun invoke(
        serie: Serie,
        onSuccess: (success: Boolean) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    ) = serieRepository.delete(serie, onSuccess, onFailure)
}