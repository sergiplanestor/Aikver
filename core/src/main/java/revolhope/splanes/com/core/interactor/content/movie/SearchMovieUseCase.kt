package revolhope.splanes.com.core.interactor.content.movie

import revolhope.splanes.com.core.data.repository.ContentRepository
import revolhope.splanes.com.core.domain.model.content.movie.Movie
import revolhope.splanes.com.core.interactor.BaseUseCase

class SearchMovieUseCase(
    private val contentRepository: ContentRepository
) : BaseUseCase<SearchMovieUseCase.Request, List<Movie>>() {

    override suspend fun execute(req: Request): List<Movie>? =
        contentRepository.searchMovies(req.query)

    data class Request(val query: String)
}