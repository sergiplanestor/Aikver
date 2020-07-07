package revolhope.splanes.com.core.interactor.content.movie

import revolhope.splanes.com.core.data.repository.ContentRepository
import revolhope.splanes.com.core.domain.model.content.movie.RelatedMovies

class FetchRelatedMoviesUseCase(private val contentRepository: ContentRepository) {
    suspend operator fun invoke(movieId: Int, page: Int): RelatedMovies? =
        contentRepository.fetchRelatedMovies(movieId, page)
}