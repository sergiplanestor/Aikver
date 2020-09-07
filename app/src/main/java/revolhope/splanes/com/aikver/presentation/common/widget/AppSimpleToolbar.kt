package revolhope.splanes.com.aikver.presentation.common.widget

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_onboarding.view.back
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.presentation.common.dpToPx

class AppSimpleToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : Toolbar(context, attrs, defStyleAttr) {

    private val titleTextView: TextView
    private val closeImageView: ImageView

    init {
        setBackgroundColor(ContextCompat.getColor(context, android.R.color.white))
        titleTextView = TextView(ContextThemeWrapper(context, R.style.Text_Bold)).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            ).apply { marginStart = dpToPx(context, 15) }
            gravity = Gravity.CENTER
        }
        closeImageView = ImageView(context).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.START
                marginStart = dpToPx(context, 12)
                dpToPx(context, 6).run { setPadding(this, this, this, this) }
            }
            setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_close))
            imageTintList =
                ColorStateList.valueOf(ContextCompat.getColor(context, android.R.color.black))
            setBackgroundResource(R.drawable.button_selector)
        }
        addView(closeImageView)
        addView(titleTextView)
    }

    fun setOnCloseClick(action: () -> Unit) {
        closeImageView.setOnClickListener { action.invoke() }
    }

    override fun setTitle(title: CharSequence?) {
        titleTextView.text = title
    }
}