package revolhope.splanes.com.aikver.presentation.feature.menu.dashboard

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import revolhope.splanes.com.aikver.presentation.common.base.BaseViewModel
import revolhope.splanes.com.core.domain.model.content.Content
import revolhope.splanes.com.core.domain.model.content.ContentDetails
import revolhope.splanes.com.core.domain.model.content.CustomContent
import revolhope.splanes.com.core.interactor.content.FetchGroupContentUseCase
import revolhope.splanes.com.core.interactor.content.movie.FetchPopularMoviesUseCase
import revolhope.splanes.com.core.interactor.content.serie.FetchPopularSeriesUseCase
import revolhope.splanes.com.core.interactor.user.FetchUserUseCase

class DashboardViewModel(
    context: Context,
    private val fetchUserUseCase: FetchUserUseCase,
    private val fetchPopularSeriesUseCase: FetchPopularSeriesUseCase,
    private val fetchPopularMoviesUseCase: FetchPopularMoviesUseCase,
    private val fetchGroupContentUseCase: FetchGroupContentUseCase
) : BaseViewModel(context) {

    private val _popularContent = MutableLiveData<List<Content>>()
    val popularContent: LiveData<List<Content>> get() = _popularContent

    private val _groupContent = MutableLiveData<List<CustomContent<ContentDetails>>>()
    val groupContent: LiveData<List<CustomContent<ContentDetails>>> get() = _groupContent

    private val _recommendedContent = MutableLiveData<List<CustomContent<ContentDetails>>>()
    val recommendedContent: LiveData<List<CustomContent<ContentDetails>>> get() = _recommendedContent

    fun fetchPopularContent() {
        launchAsync(showLoader = false) {
            val series: List<Content>? = handleResponse(
                state = fetchPopularSeriesUseCase.invoke(
                    FetchPopularSeriesUseCase.Request(page = 1)
                )
            )?.results
            val movies: List<Content>? = handleResponse(
                state = fetchPopularMoviesUseCase.invoke(
                    FetchPopularMoviesUseCase.Request(page = 1)
                )
            )?.results
            _popularContent.postValue(
                mutableListOf<Content>().apply {
                    if (series != null) addAll(series)
                    if (movies != null) addAll(movies)
                }
            )
        }
    }

    fun fetchGroupContent(force: Boolean = false) {
        launchAsync(showLoader = false) {
            val result = handleResponse(
                state = fetchGroupContentUseCase.invoke(FetchGroupContentUseCase.Request(force))
            ) ?: emptyList()
            val user = handleResponse(
                state = fetchUserUseCase.invoke(FetchUserUseCase.Request())
            )
            _recommendedContent.postValue(
                result.filter { content ->
                    content.recommendedTo.any { it.userId == user?.id }
                }
            )
            _groupContent.postValue(result)
        }
    }
}