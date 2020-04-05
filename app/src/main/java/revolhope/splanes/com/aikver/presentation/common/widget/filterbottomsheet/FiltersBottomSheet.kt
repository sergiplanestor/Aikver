package revolhope.splanes.com.aikver.presentation.common.widget.filterbottomsheet
/*

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.RadioButton
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.FragmentManager
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.presentation.common.widget.AppSpinner
import revolhope.splanes.com.aikver.presentation.common.widget.ScoreView
import revolhope.splanes.com.aikver.presentation.common.widget.platform.PlatformViewer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FiltersBottomSheet(
    private val onFilterSet: (response: FiltersModel) -> Unit,
    private val filtersModel: FiltersModel? = null
) : BottomSheetDialogFragment() {

    private lateinit var categoryCheckBox: CheckBox
    private lateinit var categorySpinner: AppSpinner
    private lateinit var scoreCheckBox: CheckBox
    private lateinit var scoreView: ScoreView
    private lateinit var platformCheckBox: CheckBox
    private lateinit var platformView: PlatformViewer
    private lateinit var ascendingRadioButton: RadioButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(
            R.layout.component_popup_filters,
            activity?.findViewById(android.R.id.content) as ViewGroup,
            false
        )

        bindViews(view)

        categoryCheckBox.setOnCheckedChangeListener { _, checked ->
            categorySpinner.visibility = if (checked) View.VISIBLE else View.GONE
        }

        scoreView.hideNumeric()
        scoreCheckBox.setOnCheckedChangeListener { _, checked ->
            scoreView.visibility = if (checked) View.VISIBLE else View.GONE
        }

        platformCheckBox.setOnCheckedChangeListener { _, checked ->
            platformView.visibility = if (checked) View.VISIBLE else View.GONE
        }

        view.findViewById<AppCompatButton>(R.id.popupNegativeButton).setOnClickListener {
            dialog?.dismiss()
        }

        view.findViewById<AppCompatButton>(R.id.popupPositiveButton).setOnClickListener {
            onFilterSet.invoke(getFilterData())
            dialog?.dismiss()
        }

        if (!filtersModel?.category.isNullOrBlank()) {
            categorySpinner.setItemSelected(filtersModel?.category)
            categoryCheckBox.isChecked = true
        }

        if (filtersModel?.score != null) {
            scoreView.updateScore(filtersModel.score)
            scoreCheckBox.isChecked = true
        }

        if (filtersModel?.platform != null) {
            platformView.setSelectedPlatform(filtersModel.platform)
            platformCheckBox.isChecked = true
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.viewTreeObserver.addOnGlobalLayoutListener(
            object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    val bottomDialog = dialog as BottomSheetDialog?
                    val bottomSheet =
                        bottomDialog?.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
                    if (bottomSheet != null) {
                        val behavior = BottomSheetBehavior.from(bottomSheet)
                        behavior.state = BottomSheetBehavior.STATE_EXPANDED
                        behavior.peekHeight = 0
                    }
                    view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            }
        )
    }

    private fun bindViews(view: View) {
        categoryCheckBox = view.findViewById(R.id.categoryCheckBox)
        categorySpinner = view.findViewById(R.id.categorySpinner)
        scoreCheckBox = view.findViewById(R.id.scoreCheckBox)
        scoreView = view.findViewById(R.id.scoreView)
        platformCheckBox = view.findViewById(R.id.platformCheckBox)
        platformView = view.findViewById(R.id.platformView)
        ascendingRadioButton = view.findViewById(R.id.orderByAscending)
    }

    private fun getFilterData(): FiltersModel =
        FiltersModel(
            category = if (categoryCheckBox.isChecked) categorySpinner.getSelected() else null,
            score = if (scoreCheckBox.isChecked) scoreView.getScore() else null,
            platform = if (platformCheckBox.isChecked) platformView.getSelectedPlatform() else null,
            orderBy = if (ascendingRadioButton.isChecked) FiltersModel.OrderBy.ASCENDING
            else FiltersModel.OrderBy.DESCENDING
        )

    fun show(fragmentManager: FragmentManager) = show(fragmentManager, this::javaClass.name)
}*/
