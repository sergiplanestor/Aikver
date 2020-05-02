package revolhope.splanes.com.core.interactor.content

import revolhope.splanes.com.core.data.repository.ContentRepository
import revolhope.splanes.com.core.domain.model.content.movie.Movie

class SearchMovieUseCase(private val contentRepository: ContentRepository) {
    suspend operator fun invoke(query: String): List<Movie>? = contentRepository.searchMovies(query)
}