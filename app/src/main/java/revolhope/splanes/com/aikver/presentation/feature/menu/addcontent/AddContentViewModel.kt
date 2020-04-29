package revolhope.splanes.com.aikver.presentation.feature.menu.addcontent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import revolhope.splanes.com.aikver.presentation.common.base.BaseViewModel
import revolhope.splanes.com.core.domain.model.Movie
import revolhope.splanes.com.core.domain.model.Serie
import revolhope.splanes.com.core.interactor.content.SearchMovieUseCase
import revolhope.splanes.com.core.interactor.content.SearchSerieUseCase

class AddContentViewModel(
    private val searchSerieUseCase: SearchSerieUseCase,
    private val searchMovieUseCase: SearchMovieUseCase
) : BaseViewModel() {

    val movieResults: LiveData<List<Movie>> get() = _movieResults
    private val _movieResults: MutableLiveData<List<Movie>> = MutableLiveData()

    val serieResults: LiveData<List<Serie>> get() = _serieResults
    private val _serieResults: MutableLiveData<List<Serie>> = MutableLiveData()

    fun fetchContent(query: String, type: Int) {
        launchAsync {
            if (type == 0) {
                _movieResults.postValue(searchMovieUseCase.invoke(query) ?: listOf())
            } else {
                _serieResults.postValue(searchSerieUseCase.invoke(query) ?: listOf())
            }
        }
    }

}