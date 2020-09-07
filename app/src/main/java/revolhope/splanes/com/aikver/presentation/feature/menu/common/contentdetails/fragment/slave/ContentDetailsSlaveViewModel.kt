package revolhope.splanes.com.aikver.presentation.feature.menu.common.contentdetails.fragment.slave

import android.content.Context
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
    context: Context,
    private val fetchUserUseCase: FetchUserUseCase,
    private val insertSerieUseCase: InsertSerieUseCase,
    private val insertMovieUseCase: InsertMovieUseCase
) : BaseViewModel(context) {

    val user: LiveData<User?> get() = _user
    private val _user: MutableLiveData<User?> = MutableLiveData()

    val addContentResult: LiveData<Boolean> get() = _addContentResult
    private val _addContentResult: MutableLiveData<Boolean> = MutableLiveData()

    fun fetchUser() {
        launchAsync {
            handleResponse(
                state = fetchUserUseCase.invoke(FetchUserUseCase.Request())
            ).also { _user.postValue(it) }
        }
    }

    fun addContent(model: ContentCustomInfoUiModel) {
        launchAsync {
            handleResponse(
                state = when (model.content) {
                    is SerieDetails -> insertSerieUseCase.invoke(
                        InsertSerieUseCase.Request(
                            serie = model.content,
                            haveSeen = model.haveSeen,
                            score = model.score,
                            network = model.network,
                            recommendedTo = model.recommendedTo,
                            comments = model.comments
                        )
                    )
                    else /* is MovieDetails */ -> insertMovieUseCase.invoke(
                        InsertMovieUseCase.Request(
                            movie = model.content as MovieDetails,
                            haveSeen = model.haveSeen,
                            score = model.score,
                            network = model.network,
                            recommendedTo = model.recommendedTo,
                            comments = model.comments
                        )
                    )
                }
            )?.let { _addContentResult.postValue(it) }
        }
    }
}