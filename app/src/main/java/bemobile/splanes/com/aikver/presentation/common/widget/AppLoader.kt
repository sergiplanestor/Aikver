package bemobile.splanes.com.aikver.presentation.common.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import bemobile.splanes.com.aikver.R
import kotlinx.android.synthetic.main.component_app_loader.view.*

class AppLoader @JvmOverloads constructor(

    context: Context,
    attrs: AttributeSet? = null,
    intDefStyle: Int = 0

) : LinearLayout (context, attrs, intDefStyle) {

    init {
        View.inflate(context, R.layout.component_app_loader, this)
    }

    fun play() {
        visibility = View.VISIBLE
        lottie.playAnimation()
    }

    fun stop() {
        if (lottie.isAnimating) lottie.cancelAnimation()
        visibility = View.GONE
    }
}
