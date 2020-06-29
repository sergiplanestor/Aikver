package revolhope.splanes.com.aikver.presentation.feature.menu.common.content

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import revolhope.splanes.com.aikver.presentation.common.base.BaseViewModel
import revolhope.splanes.com.core.interactor.content.serie.InsertSerieUseCase

class SerieDetailsSlaveViewModel(
    private val insertSerieUseCase: InsertSerieUseCase
) : BaseViewModel() {

    val addSerieResult: LiveData<Boolean> get() = _addSerieResult
    private val _addSerieResult: MutableLiveData<Boolean> = MutableLiveData()

    fun addSerie(model: SerieCustomInfoUiModel) {
        launchAsync {
            _addSerieResult.postValue(
                insertSerieUseCase.invoke(
                    model.serie,
                    model.haveSeen,
                    model.score,
                    model.network,
                    model.comments
                )
            )
        }
    }
}