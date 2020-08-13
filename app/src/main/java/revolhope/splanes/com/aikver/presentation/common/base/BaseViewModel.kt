package revolhope.splanes.com.aikver.presentation.common.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel() {

    val loaderState: LiveData<Boolean> get() = _loaderState
    private val _loaderState: MutableLiveData<Boolean> = MutableLiveData()

    val errorState: LiveData<Any?> get() = _errorState
    private val _errorState: MutableLiveData<Any?> = MutableLiveData()

    fun postLoader(show: Boolean) {
        _loaderState.postValue(show)
    }

    protected fun postError(any: Any?) { _errorState.postValue(any) }

    protected fun launchAsync(
        showLoader: Boolean = true,
        context: CoroutineContext = Dispatchers.IO,
        closure: suspend () -> Unit
    ) {
        revolhope.splanes.com.aikver.framework.app.launchAsync(context) {
            if (showLoader) _loaderState.postValue(true)
            closure.invoke()
            if (showLoader) _loaderState.postValue(false)
        }
    }

    fun errorConsumer(): (error: Throwable) -> Unit {
        return {

            // Show pop up or something like that?
            it.printStackTrace()
        }
    }
}