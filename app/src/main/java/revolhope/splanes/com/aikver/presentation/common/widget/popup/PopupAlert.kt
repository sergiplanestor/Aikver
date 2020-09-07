package revolhope.splanes.com.aikver.presentation.common.widget.popup

import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.widget.TextViewCompat
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.presentation.common.base.BaseDialog
import revolhope.splanes.com.aikver.presentation.common.dpToPx
import revolhope.splanes.com.aikver.presentation.common.invisible

class PopupAlert(private val model: PopupModel): BaseDialog() {

    override val isPopupCancelable: Boolean get() = model.isCancelable

    override fun getLayoutResource(): Int = R.layout.component_popup

    override fun initViews(view: View) {
        view.findViewById<TextView>(R.id.popupTitle).text = model.title
        view.findViewById<TextView>(R.id.popupMessage).text = model.message
        view.findViewById<AppCompatButton>(R.id.popupPositiveButton).run {
            this.text = model.buttonPositive
            this.setOnClickListener {
                model.buttonPositiveListener?.invoke()
                dismiss()
            }
            setAutoSize(this)
        }

        if (model.buttonNegative != null) {
            view.findViewById<AppCompatButton>(R.id.popupNegativeButton).run {
                this.text = model.buttonNegative
                this.setOnClickListener {
                    model.buttonNegativeListener?.invoke()
                    dismiss()
                }
                setAutoSize(this)
            }
        } else {
            view.findViewById<AppCompatButton>(R.id.popupNegativeButton).invisible()
            view.findViewById<View>(R.id.popupSeparator).invisible()
        }
    }

    private fun setAutoSize(view: TextView) {
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(
            view,
            9,
            14,
            2,
            TypedValue.COMPLEX_UNIT_DIP
        )
    }
}