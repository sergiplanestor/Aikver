package revolhope.splanes.com.aikver.presentation.common.base

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.transition.TransitionManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.framework.app.observe
import revolhope.splanes.com.aikver.presentation.common.visibility
import revolhope.splanes.com.aikver.presentation.common.widget.AppLoader

abstract class BaseBottomSheet(
    private val onDismiss: (() -> Unit)? = null
) : BottomSheetDialogFragment() {

    open val includeLoader = true
    open val stateExpanded = true

    private var mLoader: AppLoader? = null
    private lateinit var mContent: ViewGroup
    private lateinit var mRoot: ViewGroup

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        if (includeLoader && context != null) {
            inflater.inflate(
                R.layout.component_base_bottom_sheet_loader,
                container,
                false
            ).also {
                mRoot = it as ViewGroup
                mLoader = it.findViewById(R.id.appLoader)
                mContent = it.findViewById<FrameLayout>(R.id.content).apply {
                    addView(View.inflate(context, getLayoutResource(), null))
                }
            }
        } else {
            inflater.inflate(getLayoutResource(), container, false)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindView(view)
        if (stateExpanded) expand(view)
    }

    override fun dismiss() {
        super.dismiss()
        onDismiss?.invoke()
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        onDismiss?.invoke()
    }

    fun observeLoader(liveData: LiveData<Boolean>) = observe(liveData) { showLoader(it) }

    fun show(fm: FragmentManager) = show(fm, javaClass.name)

    private fun showLoader(show: Boolean) {
        if (mLoader != null) {
            mContent.visibility(!show, isGone = false)
            mLoader!!.visibility(show)
            TransitionManager.beginDelayedTransition(mRoot)
        }
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

    abstract fun getLayoutResource(): Int

    abstract fun bindView(view: View)
}