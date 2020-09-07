package revolhope.splanes.com.core.interactor.content.movie

import revolhope.splanes.com.core.data.repository.ContentRepository
import revolhope.splanes.com.core.domain.model.content.Network
import revolhope.splanes.com.core.domain.model.content.movie.Movie
import revolhope.splanes.com.core.domain.model.content.movie.MovieDetails
import revolhope.splanes.com.core.domain.model.user.UserGroupMember
import revolhope.splanes.com.core.interactor.BaseUseCase

class InsertMovieUseCase(
    private val contentRepository: ContentRepository
) : BaseUseCase<InsertMovieUseCase.Request, Boolean>() {

    override suspend fun execute(req: Request): Boolean? =
        contentRepository.insertMovie(
            movie = req.movie,
            seenByUser = req.haveSeen,
            userPunctuation = req.score,
            network = req.network,
            recommendedTo = req.recommendedTo,
            userComments = req.comments
        )

    data class Request(
        val movie: MovieDetails,
        val haveSeen: Boolean = false,
        val score: Int = -1,
        val network: Network = Network.UNKNOWN,
        val recommendedTo: List<UserGroupMember>,
        val comments: String = ""
    )
}