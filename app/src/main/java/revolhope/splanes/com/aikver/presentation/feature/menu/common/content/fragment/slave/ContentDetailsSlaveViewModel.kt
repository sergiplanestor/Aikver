package revolhope.splanes.com.aikver.presentation.feature.menu.common.content.fragment.slave

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import revolhope.splanes.com.aikver.presentation.common.base.BaseViewModel
import revolhope.splanes.com.core.domain.model.content.movie.Movie
import revolhope.splanes.com.core.domain.model.content.serie.Serie
import revolhope.splanes.com.core.domain.model.user.UserGroup
import revolhope.splanes.com.core.interactor.content.movie.InsertMovieUseCase
import revolhope.splanes.com.core.interactor.content.serie.InsertSerieUseCase
import revolhope.splanes.com.core.interactor.user.FetchUserUseCase

class ContentDetailsSlaveViewModel (
    private val fetchUserUseCase: FetchUserUseCase,
    private val insertSerieUseCase: InsertSerieUseCase,
    private val insertMovieUseCase: InsertMovieUseCase
) : BaseViewModel() {

    val userGroup: LiveData<UserGroup?> get() = _userGroup
    private val _userGroup: MutableLiveData<UserGroup?> = MutableLiveData()

    val addContentResult: LiveData<Boolean> get() = _addContentResult
    private val _addContentResult: MutableLiveData<Boolean> = MutableLiveData()

    fun fetchUserGroup() {
        launchAsync {
            _userGroup.postValue(fetchUserUseCase.invoke()?.selectedUserGroup)
        }
    }

    fun addContent(model: ContentCustomInfoUiModel) {
        launchAsync {
            _addContentResult.postValue(
                when (model.content) {
                    is Serie -> insertSerieUseCase.invoke(
                        model.content,
                        model.haveSeen,
                        model.score,
                        model.network,
                        model.recommendedTo,
                        model.comments
                    )
                    is Movie -> insertMovieUseCase.invoke(
                        model.content,
                        model.haveSeen,
                        model.score,
                        model.network,
                        model.recommendedTo,
                        model.comments
                    )
                    else -> false
                }
            )
        }
    }
}