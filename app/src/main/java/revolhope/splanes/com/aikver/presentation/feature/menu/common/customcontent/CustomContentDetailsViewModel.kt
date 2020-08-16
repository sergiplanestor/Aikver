package revolhope.splanes.com.aikver.presentation.feature.menu.common.customcontent

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
    private val fetchUserUseCase: FetchUserUseCase,
    private val fetchRelatedSeriesUseCase: FetchRelatedSeriesUseCase,
    private val fetchRelatedMoviesUseCase: FetchRelatedMoviesUseCase,
    private val addSeenByUseCase: AddSeenByUseCase
) : BaseViewModel() {

    val user: LiveData<User?> get() = _user
    private val _user: MutableLiveData<User?> = MutableLiveData()

    val contentRelated: LiveData<QueriedContent?> get() = _contentRelated
    private val _contentRelated: MutableLiveData<QueriedContent?> = MutableLiveData()

    val contentSeenByResponse: LiveData<List<UserGroupMember>> get() = _contentSeenByResponse
    private val _contentSeenByResponse: MutableLiveData<List<UserGroupMember>> = MutableLiveData()

    fun fetchUser() {
        launchAsync { _user.postValue(fetchUserUseCase.invoke()) }
    }

    fun fetchContentRelated(id: Int, isSerie: Boolean, page: Int = 1) {
        launchAsync(showLoader = false) {
            if (id != -1) {
                _contentRelated.postValue(
                    if (isSerie) {
                        fetchRelatedSeriesUseCase.invoke(id, page)
                    } else {
                        fetchRelatedMoviesUseCase.invoke(id, page)
                    }
                )
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
            _contentSeenByResponse.postValue(addSeenByUseCase.invoke(user, customContent))
        }
    }
}