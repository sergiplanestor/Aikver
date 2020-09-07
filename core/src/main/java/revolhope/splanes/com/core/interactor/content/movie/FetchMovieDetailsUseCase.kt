package revolhope.splanes.com.core.interactor.content.movie

import revolhope.splanes.com.core.data.repository.ContentRepository
import revolhope.splanes.com.core.domain.model.content.movie.MovieDetails
import revolhope.splanes.com.core.interactor.BaseUseCase

class FetchMovieDetailsUseCase(
    private val contentRepository: ContentRepository
) : BaseUseCase<FetchMovieDetailsUseCase.Request, MovieDetails>() {

    override suspend fun execute(req: Request): MovieDetails? =
        contentRepository.fetchMovieDetails(req.movieId)

    data class Request(val movieId: Int)
}