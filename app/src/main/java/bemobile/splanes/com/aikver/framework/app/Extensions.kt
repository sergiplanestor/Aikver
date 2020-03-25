package bemobile.splanes.com.aikver.framework.app

import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

fun launchAsync(context: CoroutineContext = Dispatchers.IO, action: suspend () -> Unit) {
    CoroutineScope(context).launch {
        action.invoke()
    }
}

fun View.dpToPx(context: Context, dp: Int): Int =
    dp * (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
