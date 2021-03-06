package revolhope.splanes.com.aikver.presentation.common.base

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import revolhope.splanes.com.aikver.R

abstract class BaseDialog : DialogFragment() {

    open val isPopupCancelable: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(getLayoutResource(), container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(context, R.style.AppDialogStyle)

        LayoutInflater.from(context).inflate(
            getLayoutResource(),
            null,
            false
        ).run {
            initViews(this)
            builder.setView(this)
        }
        builder.setCancelable(isPopupCancelable)
        return builder.create()
        /*return super.onCreateDialog(savedInstanceState).apply {
            window?.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.white_card))
            setCancelable(isPopupCancelable)
        }*/
    }

    fun show(fragmentManager: FragmentManager) = show(fragmentManager, this::class.java.name)

    abstract fun getLayoutResource() : Int

    abstract fun initViews(view: View)
}