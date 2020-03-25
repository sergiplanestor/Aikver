package bemobile.splanes.com.aikver.interactor

import bemobile.splanes.com.aikver.data.serie.SerieRepository
import bemobile.splanes.com.aikver.domain.Serie

class AddSerieUseCase(private val serieRepository: SerieRepository) {

    suspend operator fun invoke(

        serie: Serie,
        onSuccess: (Boolean) -> Unit,
        onFailure: (throwable: Throwable) -> Unit

    ) {
        serieRepository.insert(serie, onSuccess, onFailure)
    }

}