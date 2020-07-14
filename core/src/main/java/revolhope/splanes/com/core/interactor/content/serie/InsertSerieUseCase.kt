package revolhope.splanes.com.core.interactor.content.serie

import revolhope.splanes.com.core.data.repository.ContentRepository
import revolhope.splanes.com.core.domain.model.content.Network
import revolhope.splanes.com.core.domain.model.content.serie.Serie
import revolhope.splanes.com.core.domain.model.user.UserGroupMember

class InsertSerieUseCase(private val contentRepository: ContentRepository) {
    suspend operator fun invoke(
        serie: Serie,
        haveSeen: Boolean = false,
        score: Int = -1,
        network: Network = Network.UNKNOWN,
        recommendedTo: List<UserGroupMember>,
        comments: String = ""
    ): Boolean = contentRepository.insertSerie(
        serie = serie,
        seenByUser = haveSeen,
        userPunctuation = score,
        network = network,
        recommendedTo = recommendedTo,
        userComments = comments
    )
}