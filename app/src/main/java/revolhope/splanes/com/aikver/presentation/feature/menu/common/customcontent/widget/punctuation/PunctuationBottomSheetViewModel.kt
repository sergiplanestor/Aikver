package revolhope.splanes.com.aikver.presentation.feature.menu.common.customcontent.widget.punctuation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import revolhope.splanes.com.aikver.presentation.common.base.BaseViewModel
import revolhope.splanes.com.core.domain.model.content.ContentDetails
import revolhope.splanes.com.core.domain.model.content.CustomContent
import revolhope.splanes.com.core.domain.model.user.User
import revolhope.splanes.com.core.domain.model.user.UserGroupMember
import revolhope.splanes.com.core.interactor.content.AddPunctuationUseCase

class PunctuationBottomSheetViewModel(
    private val addPunctuationUseCase: AddPunctuationUseCase
) : BaseViewModel() {

    val onPunctuationSend: LiveData<List<Pair<UserGroupMember, Float>>> get() = _onPunctuationSend
    private val _onPunctuationSend = MutableLiveData<List<Pair<UserGroupMember, Float>>>()

    fun sendPunctuation(
        currentUser: User,
        customContent: CustomContent<ContentDetails>,
        punctuation: Int
    ) {
        launchAsync {
            _onPunctuationSend.postValue(
                addPunctuationUseCase.invoke(
                    currentUser = currentUser,
                    customContent = customContent,
                    punctuation = punctuation
                )
            )
        }
    }
}