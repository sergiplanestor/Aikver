package revolhope.splanes.com.aikver.presentation.feature.menu.searchcontent

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import revolhope.splanes.com.aikver.presentation.common.base.BaseViewModel
import revolhope.splanes.com.core.domain.model.content.movie.Movie
import revolhope.splanes.com.core.domain.model.content.serie.Serie
import revolhope.splanes.com.core.interactor.content.movie.SearchMovieUseCase
import revolhope.splanes.com.core.interactor.content.serie.SearchSerieUseCase

class SearchContentViewModel(
    context: Context,
    private val searchSerieUseCase: SearchSerieUseCase,
    private val searchMovieUseCase: SearchMovieUseCase
) : BaseViewModel(context) {

    val movieResults: LiveData<List<Movie>> get() = _movieResults
    private val _movieResults: MutableLiveData<List<Movie>> = MutableLiveData()

    val serieResults: LiveData<List<Serie>> get() = _serieResults
    private val _serieResults: MutableLiveData<List<Serie>> = MutableLiveData()

    fun fetchContent(query: String, type: Int) {
        launchAsync {
            if (type == 0) {
                handleResponse(
                    state = searchMovieUseCase.invoke(
                        SearchMovieUseCase.Request(
                            query = query
                        )
                    )
                )?.let { _movieResults.postValue(it) }
            } else {
                handleResponse(
                    state = searchSerieUseCase.invoke(
                        SearchSerieUseCase.Request(
                            query = query
                        )
                    )
                )?.let { _serieResults.postValue(it) }
            }
        }
    }

}