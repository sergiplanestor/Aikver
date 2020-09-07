package revolhope.splanes.com.aikver.presentation.feature.menu.profile.addmember

import android.view.View
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.presentation.common.base.BaseDialog

class AddMemberDialog(
    private val onPositiveButton: (String) -> Unit
) : BaseDialog() {

    override val isPopupCancelable: Boolean get() = true

    override fun getLayoutResource(): Int = R.layout.fragment_add_member_dialog

    override fun initViews(view: View) {
        view.findViewById<AppCompatButton>(R.id.popupPositiveButton).setOnClickListener {
            view.findViewById<TextInputLayout>(R.id.textLayout).error = null
            val text = view.findViewById<TextInputEditText>(R.id.textInput).text?.toString()
            if (text.isNullOrBlank()) {
                view.findViewById<TextInputLayout>(R.id.textLayout).error =
                    context?.getString(R.string.error_blank_field)
            } else {
                onPositiveButton.invoke(text)
                dismiss()
            }
        }
        view.findViewById<AppCompatButton>(R.id.popupNegativeButton).setOnClickListener {
            dismiss()
        }
    }
}