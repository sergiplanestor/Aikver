package revolhope.splanes.com.aikver.presentation.feature.menu.common.contentdetails.fragment.slave

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import revolhope.splanes.com.aikver.presentation.common.base.BaseViewModel
import revolhope.splanes.com.core.domain.model.content.movie.MovieDetails
import revolhope.splanes.com.core.domain.model.content.serie.SerieDetails
import revolhope.splanes.com.core.domain.model.user.User
import revolhope.splanes.com.core.interactor.content.movie.InsertMovieUseCase
import revolhope.splanes.com.core.interactor.content.serie.InsertSerieUseCase
import revolhope.splanes.com.core.interactor.user.FetchUserUseCase

class ContentDetailsSlaveViewModel (
    private val fetchUserUseCase: FetchUserUseCase,
    private val insertSerieUseCase: InsertSerieUseCase,
    private val insertMovieUseCase: InsertMovieUseCase
) : BaseViewModel() {

    val user: LiveData<User?> get() = _user
    private val _user: MutableLiveData<User?> = MutableLiveData()

    val addContentResult: LiveData<Boolean> get() = _addContentResult
    private val _addContentResult: MutableLiveData<Boolean> = MutableLiveData()

    fun fetchUser() {
        launchAsync {
            _user.postValue(fetchUserUseCase.invoke())
        }
    }

    fun addContent(model: ContentCustomInfoUiModel) {
        launchAsync {
            _addContentResult.postValue(
                when (model.content) {
                    is SerieDetails -> insertSerieUseCase.invoke(
                        model.content,
                        model.haveSeen,
                        model.score,
                        model.network,
                        model.recommendedTo,
                        model.comments
                    )
                    is MovieDetails -> insertMovieUseCase.invoke(
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