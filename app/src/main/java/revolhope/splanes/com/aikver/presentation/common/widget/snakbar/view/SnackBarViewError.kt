package revolhope.splanes.com.aikver.presentation.common.widget.snakbar.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.presentation.common.widget.snakbar.model.SnackBarModel

class SnackBarViewError(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : SnackBarView<SnackBarModel.Error>(context, attrs, defStyleAttr) {

    private val messageView: TextView by lazy { findViewById<TextView>(R.id.message) }

    init {
        LayoutInflater.from(context).inflate(R.layout.component_snackbar_error, this, true)
    }

    override fun bind(value: SnackBarModel.Error) {
        messageView.text = value.message
    }

    override fun animateContentOut(delay: Int, duration: Int) {
        messageView.alpha = 1f
        messageView.animate().alpha(0f).setDuration(duration.toLong()).setStartDelay(delay.toLong())
            .start()
    }

    override fun animateContentIn(delay: Int, duration: Int) {
        messageView.alpha = 0f
        messageView.animate().alpha(1f).setDuration(duration.toLong()).setStartDelay(delay.toLong())
            .start()
    }
}