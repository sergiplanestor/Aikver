package bemobile.splanes.com.aikver.presentation.common.widget.popup

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment
import bemobile.splanes.com.aikver.R

class PopupAlert(private val model: PopupModel): DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(context)
        val view = LayoutInflater.from(context).inflate(
            R.layout.component_popup,
            activity?.findViewById(android.R.id.content) as ViewGroup,
            false
        )

        view.findViewById<TextView>(R.id.popupTitle).text = model.title
        view.findViewById<TextView>(R.id.popupMessage).text = model.message
        view.findViewById<AppCompatButton>(R.id.popupPositiveButton).run {
            this.text = model.buttonPositive
            this.setOnClickListener {
                model.buttonPositiveListener.onClick(this)
                dialog?.dismiss()
            }
        }

        if (model.buttonNegative != null) {
            view.findViewById<AppCompatButton>(R.id.popupNegativeButton).run {
                this.text = model.buttonNegative
                this.setOnClickListener {
                    model.buttonNegativeListener?.onClick(this)
                    dialog?.dismiss()
                }
            }
        } else {
            view.findViewById<AppCompatButton>(R.id.popupNegativeButton).visibility = View.GONE
            view.findViewById<View>(R.id.popupSeparator).visibility = View.GONE
        }

        builder.setCancelable(model.isCancelable)
        builder.setView(view)

        val dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dialog
    }
}