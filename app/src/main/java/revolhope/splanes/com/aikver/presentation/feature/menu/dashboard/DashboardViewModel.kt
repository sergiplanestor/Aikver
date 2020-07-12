package revolhope.splanes.com.aikver.presentation.feature.menu.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import revolhope.splanes.com.aikver.presentation.common.base.BaseViewModel
import revolhope.splanes.com.core.domain.model.content.Content
import revolhope.splanes.com.core.domain.model.content.QueriedContent
import revolhope.splanes.com.core.domain.model.content.movie.QueriedMovies
import revolhope.splanes.com.core.domain.model.content.serie.QueriedSeries
import revolhope.splanes.com.core.interactor.content.movie.FetchPopularMoviesUseCase
import revolhope.splanes.com.core.interactor.content.serie.FetchPopularSeriesUseCase

class DashboardViewModel(
    private val fetchPopularSeriesUseCase: FetchPopularSeriesUseCase,
    private val fetchPopularMoviesUseCase: FetchPopularMoviesUseCase
) : BaseViewModel() {

    private val _popularContent = MutableLiveData<List<Content>>()
    val popularContent: LiveData<List<Content>> get() = _popularContent

    fun fetchPopularContent() {
        launchAsync(showLoader = false) {
            val series: List<Content>? = fetchPopularSeriesUseCase.invoke(1)?.results
            val movies: List<Content>? = fetchPopularMoviesUseCase.invoke(1)?.results
            _popularContent.postValue(
                mutableListOf<Content>().apply {
                    if (series != null) addAll(series)
                    if (movies != null) addAll(movies)
                }
            )
        }
    }
}