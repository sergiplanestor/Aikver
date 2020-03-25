package bemobile.splanes.com.aikver.presentation.detailsserie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import bemobile.splanes.com.aikver.domain.Serie
import bemobile.splanes.com.aikver.framework.app.launchAsync
import bemobile.splanes.com.aikver.interactor.DeleteSerieUseCase
import bemobile.splanes.com.aikver.interactor.UpdateSerieUseCase
import bemobile.splanes.com.aikver.presentation.common.base.BaseViewModel

class DetailsSerieViewModel(
    private val updateSerieUseCase: UpdateSerieUseCase,
    private val deleteSerieUseCase: DeleteSerieUseCase
): BaseViewModel() {

    fun updateSerie(serie: Serie): LiveData<Boolean> {

        val liveData = MutableLiveData<Boolean>()
        launchAsync {
            updateSerieUseCase.invoke(
                serie,
                {
                    liveData.postValue(it)
                },
                errorConsumer()
            )
        }
        return liveData
    }

    fun deleteSerie(serie: Serie): LiveData<Boolean> {

        val liveData = MutableLiveData<Boolean>()
        launchAsync {
            deleteSerieUseCase.invoke(
                serie,
                {
                    liveData.postValue(it)
                },
                errorConsumer()
            )
        }
        return liveData
    }
}