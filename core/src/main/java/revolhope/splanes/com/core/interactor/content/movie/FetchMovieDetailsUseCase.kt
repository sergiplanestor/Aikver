package revolhope.splanes.com.core.interactor.content.movie

import revolhope.splanes.com.core.data.repository.ContentRepository
import revolhope.splanes.com.core.domain.model.content.movie.MovieDetails

class FetchMovieDetailsUseCase(private val contentRepository: ContentRepository) {
    suspend operator fun invoke(movieId: Int): MovieDetails? =
        contentRepository.fetchMovieDetails(movieId)
}