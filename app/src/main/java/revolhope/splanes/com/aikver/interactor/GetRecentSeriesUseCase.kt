package revolhope.splanes.com.aikver.interactor

import revolhope.splanes.com.aikver.data.serie.SerieRepository
import revolhope.splanes.com.aikver.domain.Serie

class GetRecentSeriesUseCase(private val serieRepository: SerieRepository) {

    suspend operator fun invoke(

        forceCall: Boolean,
        onSuccess: (List<Serie>) -> Unit,
        onFailure: (throwable: Throwable) -> Unit

    ) {
        serieRepository.fetchRecent(onSuccess, onFailure, forceCall)
    }

}