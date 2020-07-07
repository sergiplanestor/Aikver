package revolhope.splanes.com.aikver.framework.app

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

fun launchAsync(
    context: CoroutineContext = Dispatchers.IO,
    action: suspend () -> Unit
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

fun FragmentManager.exec(operation: (FragmentTransaction) -> Unit) {
    beginTransaction().apply {
        operation.invoke(this)
        commitAllowingStateLoss()
    }
}

fun FragmentManager.findByTag(tag: String) : Fragment? = fragments.find { it.tag == tag }

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
        view.windowToken,
        0
    )
}