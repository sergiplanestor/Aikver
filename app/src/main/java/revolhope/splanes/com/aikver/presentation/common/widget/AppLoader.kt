package revolhope.splanes.com.aikver.presentation.common.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.component_app_loader.view.*
import revolhope.splanes.com.aikver.R

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
