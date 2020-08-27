package revolhope.splanes.com.aikver.presentation.feature.menu.common.customcontent

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import revolhope.splanes.com.aikver.presentation.common.base.BaseViewModel
import revolhope.splanes.com.core.domain.model.content.ContentDetails
import revolhope.splanes.com.core.domain.model.content.CustomContent
import revolhope.splanes.com.core.domain.model.content.QueriedContent
import revolhope.splanes.com.core.domain.model.user.User
import revolhope.splanes.com.core.domain.model.user.UserGroupMember
import revolhope.splanes.com.core.interactor.content.AddSeenByUseCase
import revolhope.splanes.com.core.interactor.content.movie.FetchRelatedMoviesUseCase
import revolhope.splanes.com.core.interactor.content.serie.FetchRelatedSeriesUseCase
import revolhope.splanes.com.core.interactor.user.FetchUserUseCase

class CustomContentDetailsViewModel(
    context: Context,
    private val fetchUserUseCase: FetchUserUseCase,
    private val fetchRelatedSeriesUseCase: FetchRelatedSeriesUseCase,
    private val fetchRelatedMoviesUseCase: FetchRelatedMoviesUseCase,
    private val addSeenByUseCase: AddSeenByUseCase
) : BaseViewModel(context) {

    val user: LiveData<User> get() = _user
    private val _user: MutableLiveData<User> = MutableLiveData()

    val contentRelated: LiveData<QueriedContent?> get() = _contentRelated
    private val _contentRelated: MutableLiveData<QueriedContent?> = MutableLiveData()

    val contentSeenByResponse: LiveData<List<UserGroupMember>> get() = _contentSeenByResponse
    private val _contentSeenByResponse: MutableLiveData<List<UserGroupMember>> = MutableLiveData()

    fun fetchUser() =
        launchAsync {
            handleResponse(
                state = fetchUserUseCase.invoke(FetchUserUseCase.Request())
            )?.let { _user.postValue(it) }
        }

    fun fetchContentRelated(id: Int, isSerie: Boolean, page: Int = 1) {
        launchAsync(showLoader = false) {
            if (id != -1) {
                handleResponse(
                    state = if (isSerie) {
                        fetchRelatedSeriesUseCase.invoke(
                            FetchRelatedSeriesUseCase.Request(
                                serieId = id,
                                page = page
                            )
                        )
                    } else {
                        fetchRelatedMoviesUseCase.invoke(
                            FetchRelatedMoviesUseCase.Request(
                                movieId = id,
                                page = page
                            )
                        )
                    }
                )?.let { _contentRelated.postValue(it) }
            } else {
                _contentRelated.postValue(null)
            }
        }
    }

    fun onContentSeenBy(
        user: User,
        customContent: CustomContent<ContentDetails>
    ) {
        launchAsync {
            handleResponse(
                state = addSeenByUseCase.invoke(
                    AddSeenByUseCase.Request(
                        currentUser = user,
                        customContent = customContent
                    )
                )
            )?.let { _contentSeenByResponse.postValue(it) }
        }
    }
}