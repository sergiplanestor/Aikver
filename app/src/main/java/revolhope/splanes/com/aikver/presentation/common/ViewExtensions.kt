package revolhope.splanes.com.aikver.presentation.common

import android.content.Context
import android.util.DisplayMetrics
import android.view.View

fun dpToPx(context: Context, dp: Int): Int =
    dp * (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)

fun View.visible() { this.visibility = View.VISIBLE }

fun View.invisible() { this.visibility = View.GONE }

fun View.visibility(show: Boolean) = if (show) this.visible() else this.invisible()