package revolhope.splanes.com.aikver.presentation.feature.menu.common.content.fragment.master

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
    private val fetchSerieDetailsUseCase: FetchSerieDetailsUseCase,
    private val fetchMovieDetailsUseCase: FetchMovieDetailsUseCase,
    private val fetchRelatedSeriesUseCase: FetchRelatedSeriesUseCase,
    private val fetchRelatedMoviesUseCase: FetchRelatedMoviesUseCase
) : BaseViewModel() {

    val contentDetails: LiveData<ContentDetails?> get() = _contentDetails
    private val _contentDetails: MutableLiveData<ContentDetails?> = MutableLiveData()

    val contentRelated: LiveData<QueriedContent?> get() = _contentRelated
    private val _contentRelated: MutableLiveData<QueriedContent?> = MutableLiveData()

    fun fetchDetails(id: Int, isSerie: Boolean) {
        launchAsync {
            if (id != -1) {
                _contentDetails.postValue(
                    if (isSerie) {
                        fetchSerieDetailsUseCase.invoke(id)
                    } else {
                        fetchMovieDetailsUseCase.invoke(id)
                    }
                )
            } else {
                _contentDetails.postValue(null)
            }
        }
    }

    fun fetchContentRelated(id: Int, isSerie: Boolean, page: Int = 1) {
        launchAsync(showLoader = false) {
            if (id != -1) {
                _contentRelated.postValue(
                    if (isSerie) {
                        fetchRelatedSeriesUseCase.invoke(id, page)
                    } else {
                        fetchRelatedMoviesUseCase.invoke(id, page)
                    }
                )
            } else {
                _contentRelated.postValue(null)
            }
        }
    }
}