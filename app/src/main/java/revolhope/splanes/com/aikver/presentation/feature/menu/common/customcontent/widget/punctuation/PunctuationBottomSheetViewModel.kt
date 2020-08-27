package revolhope.splanes.com.aikver.presentation.feature.menu.common.customcontent.widget.punctuation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import revolhope.splanes.com.aikver.presentation.common.base.BaseViewModel
import revolhope.splanes.com.core.domain.model.content.ContentDetails
import revolhope.splanes.com.core.domain.model.content.CustomContent
import revolhope.splanes.com.core.domain.model.user.User
import revolhope.splanes.com.core.domain.model.user.UserGroupMember
import revolhope.splanes.com.core.interactor.content.AddPunctuationUseCase

class PunctuationBottomSheetViewModel(
    context: Context,
    private val addPunctuationUseCase: AddPunctuationUseCase
) : BaseViewModel(context) {

    val onPunctuationSend: LiveData<List<Pair<UserGroupMember, Float>>> get() = _onPunctuationSend
    private val _onPunctuationSend = MutableLiveData<List<Pair<UserGroupMember, Float>>>()

    fun sendPunctuation(
        currentUser: User,
        customContent: CustomContent<ContentDetails>,
        punctuation: Int
    ) {
        launchAsync {
            handleResponse(
                state = addPunctuationUseCase.invoke(
                    AddPunctuationUseCase.Request(
                        currentUser = currentUser,
                        customContent = customContent,
                        punctuation = punctuation
                    )
                )
            )?.let { _onPunctuationSend.postValue(it) }
        }
    }
}