package revolhope.splanes.com.aikver.framework.app

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

inline fun launchAsync(
    context: CoroutineContext = Dispatchers.IO,
    crossinline action: suspend () -> Unit
) {
    CoroutineScope(context).launch {
        action.invoke()
    }
}

fun <T> LifecycleOwner.observe(data: LiveData<T>, closure: (data: T) -> Unit) {
    data.observe(this, Observer {
        closure.invoke(it)
    })
}
