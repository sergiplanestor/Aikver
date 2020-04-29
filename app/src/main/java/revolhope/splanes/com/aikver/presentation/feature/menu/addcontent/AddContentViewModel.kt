package revolhope.splanes.com.aikver.presentation.feature.menu.addcontent

import revolhope.splanes.com.aikver.presentation.common.base.BaseViewModel
import revolhope.splanes.com.core.interactor.content.SearchContentUseCase

class AddContentViewModel(
    private val searchContentUseCase: SearchContentUseCase
) : BaseViewModel() {



    fun fetchContent(query: String, type: Int) {
        launchAsync {
            searchContentUseCase.invoke(query, type)
        }
    }

}