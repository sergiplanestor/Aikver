package revolhope.splanes.com.core.interactor.content.serie

import revolhope.splanes.com.core.data.repository.ContentRepository
import revolhope.splanes.com.core.domain.model.content.Network
import revolhope.splanes.com.core.domain.model.content.serie.SerieDetails
import revolhope.splanes.com.core.domain.model.user.UserGroupMember
import revolhope.splanes.com.core.interactor.BaseUseCase

class InsertSerieUseCase(
    private val contentRepository: ContentRepository
) : BaseUseCase<InsertSerieUseCase.Request, Boolean>() {

    override suspend fun execute(req: Request): Boolean? = contentRepository.insertSerie(
        serie = req.serie,
        seenByUser = req.haveSeen,
        userPunctuation = req.score,
        network = req.network,
        recommendedTo = req.recommendedTo,
        userComments = req.comments
    )

    data class Request(
        val serie: SerieDetails,
        val haveSeen: Boolean = false,
        val score: Int = -1,
        val network: Network = Network.UNKNOWN,
        val recommendedTo: List<UserGroupMember>,
        val comments: String = ""
    )
}