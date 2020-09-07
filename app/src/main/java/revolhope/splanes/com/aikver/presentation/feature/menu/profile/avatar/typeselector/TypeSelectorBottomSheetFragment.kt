package revolhope.splanes.com.aikver.presentation.feature.menu.profile.avatar.typeselector

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_avatar_type_selector_bottom_sheet.doneButton
import kotlinx.android.synthetic.main.fragment_avatar_type_selector_bottom_sheet.titleTextView
import kotlinx.android.synthetic.main.fragment_avatar_type_selector_bottom_sheet.typesRecyclerView
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.presentation.common.base.BaseBottomSheet

class TypeSelectorBottomSheetFragment(
    private val title: String,
    private val items: List<TypeSelectorUiModel>,
    private val callback: (TypeSelectorUiModel?) -> Unit
) : BaseBottomSheet() {

    override fun getLayoutResource(): Int = R.layout.fragment_avatar_type_selector_bottom_sheet

    override fun bindView(view: View) {
        titleTextView.text = getString(R.string.type_of, title)
        typesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        typesRecyclerView.adapter = TypeSelectorAdapter(
            items = items
        )
        doneButton.setOnClickListener {
            callback.invoke((typesRecyclerView.adapter as TypeSelectorAdapter).selectedItem)
            dismiss()
        }
    }
}