package revolhope.splanes.com.aikver.presentation.common.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.TextSwitcher
import android.widget.TextView
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.Toolbar
import revolhope.splanes.com.aikver.R

class AppMenuToolbar @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : Toolbar(context, attributeSet, defStyleAttr) {

    private var textSwitcher: TextSwitcher = TextSwitcher(context)

    init {
        this.addView(textSwitcher.apply {
            layoutParams = LayoutParams(Gravity.CENTER)
            setFactory {
                TextView(ContextThemeWrapper(context, R.style.ToolbarMenuText)).apply {
                    layoutParams = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT
                    )
                    gravity = Gravity.CENTER
                }
            }
            inAnimation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in).apply {
                duration = 170
            }
            outAnimation = AnimationUtils.loadAnimation(context, android.R.anim.fade_out).apply {
                duration = 170
            }
        })
    }

    override fun setTitle(title: CharSequence?) = textSwitcher.setText(title)

}