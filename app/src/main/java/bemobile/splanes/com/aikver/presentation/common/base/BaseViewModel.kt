package bemobile.splanes.com.aikver.presentation.common.base

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    fun errorConsumer() : (error: Throwable) -> Unit {
        return {

            // Show pop up or something like that?
            it.printStackTrace()
        }
    }

}