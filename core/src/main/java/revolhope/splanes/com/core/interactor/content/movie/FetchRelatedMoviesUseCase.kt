package revolhope.splanes.com.core.interactor.content.movie

import revolhope.splanes.com.core.data.repository.ContentRepository
import revolhope.splanes.com.core.domain.model.content.movie.QueriedMovies
import revolhope.splanes.com.core.interactor.BaseUseCase

class FetchRelatedMoviesUseCase(
    private val contentRepository: ContentRepository
) : BaseUseCase<FetchRelatedMoviesUseCase.Request, QueriedMovies>() {

    override suspend fun execute(req: Request): QueriedMovies? =
        contentRepository.fetchRelatedMovies(movieId = req.movieId, page = req.page)

    data class Request(val movieId: Int, val page: Int)
}