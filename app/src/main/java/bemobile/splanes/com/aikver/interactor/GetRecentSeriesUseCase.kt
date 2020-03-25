package bemobile.splanes.com.aikver.interactor

import bemobile.splanes.com.aikver.data.serie.SerieRepository
import bemobile.splanes.com.aikver.domain.Serie

class GetRecentSeriesUseCase(private val serieRepository: SerieRepository) {

    suspend operator fun invoke(

        forceCall: Boolean,
        onSuccess: (List<Serie>) -> Unit,
        onFailure: (throwable: Throwable) -> Unit

    ) {
        serieRepository.fetchRecent(onSuccess, onFailure, forceCall)
    }

}