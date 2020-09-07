package revolhope.splanes.com.aikver.presentation.feature.menu.common.contentdetails.fragment.master

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import revolhope.splanes.com.aikver.presentation.common.base.BaseViewModel
import revolhope.splanes.com.core.domain.model.content.ContentDetails
import revolhope.splanes.com.core.domain.model.content.QueriedContent
import revolhope.splanes.com.core.interactor.content.movie.FetchMovieDetailsUseCase
import revolhope.splanes.com.core.interactor.content.movie.FetchRelatedMoviesUseCase
import revolhope.splanes.com.core.interactor.content.serie.FetchRelatedSeriesUseCase
import revolhope.splanes.com.core.interactor.content.serie.FetchSerieDetailsUseCase

class ContentDetailsMasterViewModel(
    context: Context,
    private val fetchSerieDetailsUseCase: FetchSerieDetailsUseCase,
    private val fetchMovieDetailsUseCase: FetchMovieDetailsUseCase,
    private val fetchRelatedSeriesUseCase: FetchRelatedSeriesUseCase,
    private val fetchRelatedMoviesUseCase: FetchRelatedMoviesUseCase
) : BaseViewModel(context) {

    val contentDetails: LiveData<ContentDetails?> get() = _contentDetails
    private val _contentDetails: MutableLiveData<ContentDetails?> = MutableLiveData()

    val contentRelated: LiveData<QueriedContent?> get() = _contentRelated
    private val _contentRelated: MutableLiveData<QueriedContent?> = MutableLiveData()

    fun fetchDetails(id: Int, isSerie: Boolean) {
        launchAsync {
            if (id != -1) {
                handleResponse(
                    state = if (isSerie) {
                        fetchSerieDetailsUseCase.invoke(
                            FetchSerieDetailsUseCase.Request(serieId = id)
                        )
                    } else {
                        fetchMovieDetailsUseCase.invoke(
                            FetchMovieDetailsUseCase.Request(movieId = id)
                        )
                    }
                )?.let { _contentDetails.postValue(it) }
            } else {
                _contentDetails.postValue(null)
            }
        }
    }

    fun fetchContentRelated(id: Int, isSerie: Boolean, page: Int = 1) {
        launchAsync(showLoader = false) {
            if (id != -1) {
                handleResponse(
                    state = if (isSerie) {
                        fetchRelatedSeriesUseCase.invoke(
                            FetchRelatedSeriesUseCase.Request(serieId = id, page = page)
                        )
                    } else {
                        fetchRelatedMoviesUseCase.invoke(
                            FetchRelatedMoviesUseCase.Request(movieId = id, page = page)
                        )
                    }
                )?.let { _contentRelated.postValue(it) }
            } else {
                _contentRelated.postValue(null)
            }
        }
    }
}