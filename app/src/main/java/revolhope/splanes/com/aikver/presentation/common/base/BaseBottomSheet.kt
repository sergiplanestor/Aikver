package revolhope.splanes.com.aikver.presentation.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheet : BottomSheetDialogFragment() {

    open val stateExpanded = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(getLayoutResource(), container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindView(view)
        if (stateExpanded) expand(view)
    }

    private fun expand(view: View) {
        view.viewTreeObserver.addOnGlobalLayoutListener(
            object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    val bottomSheetRes = com.google.android.material.R.id.design_bottom_sheet
                    (dialog as BottomSheetDialog?)?.findViewById<FrameLayout>(bottomSheetRes)?.let {
                        with(BottomSheetBehavior.from(it)) {
                            state = BottomSheetBehavior.STATE_EXPANDED
                            peekHeight = 0
                        }
                    }
                    view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            }
        )
    }

    fun show(fm: FragmentManager) = show(fm, javaClass.name)

    abstract fun getLayoutResource(): Int

    abstract fun bindView(view: View)
}