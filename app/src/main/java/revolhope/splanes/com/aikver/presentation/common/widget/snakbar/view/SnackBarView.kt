package revolhope.splanes.com.aikver.presentation.common.widget.snakbar.view

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.ContentViewCallback
import revolhope.splanes.com.aikver.presentation.common.widget.snakbar.model.SnackBarModel

abstract class SnackBarView<T : SnackBarModel> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ContentViewCallback {

    abstract fun bind(value: T)

    override fun animateContentOut(delay: Int, duration: Int) {
        // Nothing to do here
    }

    override fun animateContentIn(delay: Int, duration: Int) {
        // Nothing to do here
    }
}