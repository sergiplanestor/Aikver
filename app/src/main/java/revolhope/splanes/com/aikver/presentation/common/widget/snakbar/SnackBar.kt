package revolhope.splanes.com.aikver.presentation.common.widget.snakbar

import android.annotation.SuppressLint
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import androidx.core.os.postDelayed
import androidx.transition.TransitionManager
import com.google.android.material.snackbar.BaseTransientBottomBar
import revolhope.splanes.com.aikver.presentation.common.dpToPx
import revolhope.splanes.com.aikver.presentation.common.findSuitableParent
import revolhope.splanes.com.aikver.presentation.common.widget.snakbar.model.SnackBarModel
import revolhope.splanes.com.aikver.presentation.common.widget.snakbar.view.SnackBarView
import revolhope.splanes.com.aikver.presentation.common.widget.snakbar.view.SnackBarViewError
import revolhope.splanes.com.aikver.presentation.common.widget.snakbar.view.SnackBarViewSuccess

class SnackBar(
    parent: ViewGroup,
    content: SnackBarView<*>
) : BaseTransientBottomBar<SnackBar>(parent, content, content) {

    init {
        getView().apply {
            setBackgroundColor(context.getColor(android.R.color.transparent))
            setPadding(0, 0, 0, dpToPx(context, PADDING_BOTTOM_DP))
        }
    }

    companion object {

        private const val PADDING_BOTTOM_DP = 20
        private const val DURATION = 5000

        @SuppressLint("WrongConstant")
        fun show(view: View?, model: SnackBarModel) {

            val viewGroup = view.findSuitableParent() ?: return

            val content = when (model) {
                is SnackBarModel.Success -> {
                    SnackBarViewSuccess(viewGroup.context).apply { bind(model) }
                }
                is SnackBarModel.Error -> {
                    SnackBarViewError(viewGroup.context).apply { bind(model) }
                }
            }

            SnackBar(
                viewGroup,
                content
            ).apply {
                duration = DURATION
                content.setOnClickListener {
                    model.onClick?.invoke()
                    dismiss()
                }
                Handler().postDelayed(
                    { (model as? SnackBarModel.Success)?.onDismiss?.invoke() },
                    DURATION.toLong()
                )
            }.show()
            TransitionManager.beginDelayedTransition(viewGroup)
        }
    }
}