package revolhope.splanes.com.aikver.presentation.feature.menu.profile.addmember

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import revolhope.splanes.com.aikver.R

class AddMemberDialog(
    private val onPositiveButton: (String) -> Unit
) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_add_member_dialog, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            window?.setBackgroundDrawable(context.getDrawable(R.drawable.white_card))
        }
    }
}