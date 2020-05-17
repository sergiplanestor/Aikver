package revolhope.splanes.com.aikver.presentation.feature.menu.common.content

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import revolhope.splanes.com.aikver.presentation.common.base.BaseViewModel
import revolhope.splanes.com.core.domain.model.content.serie.Serie
import revolhope.splanes.com.core.domain.model.content.serie.SerieDetails
import revolhope.splanes.com.core.interactor.content.serie.FetchSerieDetailsUseCase
import revolhope.splanes.com.core.interactor.content.serie.InsertSerieUseCase

class SerieDetailsViewModel(
    private val fetchSerieDetailsUseCase: FetchSerieDetailsUseCase,
    private val insertSerieUseCase: InsertSerieUseCase
) : BaseViewModel() {

    val serieDetails: LiveData<SerieDetails?> get() = _serieDetails
    private val _serieDetails: MutableLiveData<SerieDetails?> = MutableLiveData()

    val addSerieResult: LiveData<Boolean> get() = _addSerieResult
    private val _addSerieResult: MutableLiveData<Boolean> = MutableLiveData()

    fun fetchDetails(id: Int) {
        launchAsync {
            _serieDetails.postValue(
                if (id == -1) {
                    null
                } else {
                    fetchSerieDetailsUseCase.invoke(id)
                }
            )
        }
    }

    fun addSerie(serie: Serie?) {
        launchAsync {
            _addSerieResult.postValue(serie?.let { insertSerieUseCase.invoke(it) } ?: false)
        }
    }
}