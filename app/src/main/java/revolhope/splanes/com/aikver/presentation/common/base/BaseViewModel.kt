package revolhope.splanes.com.aikver.presentation.common.base

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.core.domain.ResponseState
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(private val context: Context) : ViewModel() {

    val loaderState: LiveData<Boolean> get() = _loaderState
    private val _loaderState: MutableLiveData<Boolean> = MutableLiveData()

    val errorState: LiveData<String> get() = _errorState
    private val _errorState: MutableLiveData<String> = MutableLiveData()

    fun postLoader(show: Boolean) {
        _loaderState.postValue(show)
    }

    fun postError(message: String? = null) {
        _errorState.postValue(
            message ?: context.getString(R.string.default_error)
        )
    }

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

    protected suspend fun <T> handleResponse(
        state: ResponseState<T>,
        shouldPostError: Boolean = true,
        transformation: (suspend (T) -> T)? = null
    ) : T? =
        when (state) {
            is ResponseState.Success -> {
                transformation?.invoke(state.data) ?: state.data
            }
            is ResponseState.Error -> {
                if (shouldPostError) {
                    if (state.description.isNullOrBlank()) {
                        state.description = context.getString(R.string.default_error)
                    }
                    _errorState.postValue(state.description)
                }
                state.error.printStackTrace()
                null
            }
        }
}