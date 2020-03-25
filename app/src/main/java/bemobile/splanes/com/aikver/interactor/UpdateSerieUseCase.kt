package bemobile.splanes.com.aikver.interactor

import bemobile.splanes.com.aikver.data.serie.SerieRepository
import bemobile.splanes.com.aikver.domain.Serie

class UpdateSerieUseCase(private val serieRepository: SerieRepository) {
    suspend operator fun invoke(
        serie: Serie,
        onSuccess: (success: Boolean) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    ) = serieRepository.edit(serie, onSuccess, onFailure)
}