package revolhope.splanes.com.aikver.presentation.feature.menu.common.content

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import revolhope.splanes.com.aikver.presentation.common.base.BaseViewModel
import revolhope.splanes.com.core.domain.model.content.serie.Serie
import revolhope.splanes.com.core.interactor.content.serie.InsertSerieUseCase

class SerieCustomInfoViewModel(
    private val insertSerieUseCase: InsertSerieUseCase
) : BaseViewModel() {

    val addSerieResult: LiveData<Boolean> get() = _addSerieResult
    private val _addSerieResult: MutableLiveData<Boolean> = MutableLiveData()

    fun addSerie(serie: Serie?) {
        launchAsync {
            _addSerieResult.postValue(serie?.let { insertSerieUseCase.invoke(it) } ?: false)
        }
    }
}