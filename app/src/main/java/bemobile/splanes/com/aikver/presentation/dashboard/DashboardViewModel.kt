package bemobile.splanes.com.aikver.presentation.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import bemobile.splanes.com.aikver.domain.Serie
import bemobile.splanes.com.aikver.interactor.GetRecentSeriesUseCase
import bemobile.splanes.com.aikver.interactor.GetSeriesUseCase
import bemobile.splanes.com.aikver.presentation.common.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.koin.core.KoinComponent
import org.koin.core.inject

class DashboardViewModel : BaseViewModel(), KoinComponent {

    private val mGetRecentSeriesUseCase: GetRecentSeriesUseCase by inject()
    private val mGetSeriesUseCase: GetSeriesUseCase by inject()

    fun getRecentSeries(forceCall: Boolean = false): LiveData<List<Serie>> {

        val liveData =  MutableLiveData<List<Serie>>()

        runBlocking(Dispatchers.IO) {
            mGetRecentSeriesUseCase.invoke(
                forceCall,
                { liveData.postValue(it) },
                errorConsumer()
            )
        }
        return liveData
    }

    fun getAllSeries(forceCall: Boolean = false): LiveData<List<Serie>> {

        val liveData =  MutableLiveData<List<Serie>>()

        runBlocking(Dispatchers.IO) {
            mGetSeriesUseCase.invoke(
                forceCall,
                { liveData.postValue(it) },
                errorConsumer()
            )
        }
        return liveData
    }

}