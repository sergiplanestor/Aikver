package revolhope.splanes.com.aikver.interactor

import revolhope.splanes.com.aikver.data.serie.SerieRepository
import revolhope.splanes.com.aikver.domain.Serie

class AddSerieUseCase(private val serieRepository: SerieRepository) {

    suspend operator fun invoke(

        serie: Serie,
        onSuccess: (Boolean) -> Unit,
        onFailure: (throwable: Throwable) -> Unit

    ) {
        serieRepository.insert(serie, onSuccess, onFailure)
    }

}