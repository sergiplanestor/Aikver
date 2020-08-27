package revolhope.splanes.com.core.interactor.content.movie

import revolhope.splanes.com.core.data.repository.ContentRepository
import revolhope.splanes.com.core.domain.model.content.movie.QueriedMovies
import revolhope.splanes.com.core.interactor.BaseUseCase

class FetchPopularMoviesUseCase(
    private val contentRepository: ContentRepository
) : BaseUseCase<FetchPopularMoviesUseCase.Request, QueriedMovies>() {

    override suspend fun execute(req: Request): QueriedMovies? =
        contentRepository.fetchPopularMovies(req.page)

    data class Request(val page: Int)
}