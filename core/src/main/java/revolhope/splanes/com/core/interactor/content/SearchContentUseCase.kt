package revolhope.splanes.com.core.interactor.content

import revolhope.splanes.com.core.data.repository.ContentRepository

class SearchContentUseCase(private val contentRepository: ContentRepository) {
    suspend operator fun invoke(query: String, type: Int) =
        if (type == 0) /* Movies */ {
            contentRepository.searchMovies(query)
        } else /* type = 1, Series */ {
            contentRepository.searchSeries(query)
        }
}