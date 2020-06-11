package revolhope.splanes.com.aikver.presentation.feature.menu.common.content

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import revolhope.splanes.com.aikver.presentation.common.base.BaseViewModel
import revolhope.splanes.com.core.domain.model.content.Network
import revolhope.splanes.com.core.domain.model.content.serie.CustomSerie
import revolhope.splanes.com.core.domain.model.content.serie.Serie
import revolhope.splanes.com.core.interactor.content.serie.InsertSerieUseCase
import revolhope.splanes.com.core.interactor.user.FetchUserUseCase

class SerieDetailsSlaveViewModel(
    private val fetchUserUseCase: FetchUserUseCase,
    private val insertSerieUseCase: InsertSerieUseCase
) : BaseViewModel() {

    val addSerieResult: LiveData<Boolean> get() = _addSerieResult
    private val _addSerieResult: MutableLiveData<Boolean> = MutableLiveData()

    fun addSerie(model: SerieCustomInfoUiModel) {
        launchAsync {
            _addSerieResult.postValue(model.serie?.let {
                insertSerieUseCase.invoke(model)
            } ?: false)
        }
    }
}