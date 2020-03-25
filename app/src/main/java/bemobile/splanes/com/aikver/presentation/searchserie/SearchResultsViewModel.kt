package bemobile.splanes.com.aikver.presentation.searchserie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import bemobile.splanes.com.aikver.domain.Serie
import bemobile.splanes.com.aikver.framework.app.launchAsync
import bemobile.splanes.com.aikver.interactor.GetSeriesUseCase
import bemobile.splanes.com.aikver.presentation.common.base.BaseViewModel
import bemobile.splanes.com.aikver.presentation.common.widget.filterbottomsheet.FiltersModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*

class SearchResultsViewModel: BaseViewModel(), KoinComponent {

    private val mGetSeriesUseCase: GetSeriesUseCase by inject()

    fun search(model: FiltersModel): LiveData<List<Serie>> {

        val liveData = MutableLiveData<List<Serie>>()

        launchAsync {
            mGetSeriesUseCase.invoke(
                forceCall = false,
                onSuccess = { series ->
                    liveData.postValue(series.filter {
                        if (model.input != null && !it.title.contains(model.input,true)) false
                        else if (model.category != null && model.category != it.category) false
                        else if (model.score != null && model.score > it.score) false
                        else !(model.platform != null && model.platform.name != it.platform?.name)
                    }.run {
                        when (model.orderBy) {
                            FiltersModel.OrderBy.ASCENDING -> sortedBy { it.dateCreation }
                            FiltersModel.OrderBy.DESCENDING -> sortedByDescending { it.dateCreation }
                        }
                    })
                },
                onFailure = errorConsumer()
            )
        }

        return liveData
    }
}