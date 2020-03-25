package bemobile.splanes.com.aikver.presentation.common.widget.popup

import android.content.Context
import android.view.View
import androidx.fragment.app.FragmentManager
import bemobile.splanes.com.aikver.R

object Popup {

    fun showError(
        context: Context,
        fm: FragmentManager,
        onErrorAction: View.OnClickListener
    ) = show(
        fm = fm,
        model = PopupModel(
            title = context.getString(R.string.error_title),
            message = context.getString(R.string.error_message_default),
            buttonPositive = context.getString(R.string.ok),
            buttonPositiveListener = onErrorAction
        )
    )

    fun show(fm: FragmentManager, model: PopupModel) = PopupAlert(model).show(fm, "Popup")
}