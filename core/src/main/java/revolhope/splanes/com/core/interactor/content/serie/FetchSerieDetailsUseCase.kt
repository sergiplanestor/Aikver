package revolhope.splanes.com.core.interactor.content.serie

import revolhope.splanes.com.core.data.repository.ContentRepository
import revolhope.splanes.com.core.domain.model.content.serie.SerieDetails
import revolhope.splanes.com.core.interactor.BaseUseCase

class FetchSerieDetailsUseCase(
    private val contentRepository: ContentRepository
) : BaseUseCase<FetchSerieDetailsUseCase.Request, SerieDetails>() {

    override suspend fun execute(req: Request): SerieDetails? =
        contentRepository.fetchSerieDetails(serieId = req.serieId)

    data class Request(val serieId: Int)
}