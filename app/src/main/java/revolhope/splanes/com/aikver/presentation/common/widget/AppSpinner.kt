package revolhope.splanes.com.aikver.presentation.common.widget
/*

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import revolhope.splanes.com.aikver.R
import kotlinx.android.synthetic.main.component_app_spinner.view.*

class AppSpinner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleInt: Int = 0): LinearLayout(context, attrs, defStyleInt), AdapterView.OnItemSelectedListener {

    private val mItems: Array<String>
    private var mItemSelected: String? = null

    init {
        View.inflate(context, R.layout.component_app_spinner, this)
        mItems = resources.getStringArray(R.array.categories)
        initialize()
    }

    private fun initialize() {

        spinner.adapter = ArrayAdapter.createFromResource(
            context,
            R.array.categories,
            R.layout.component_app_spinner_item
        ).apply {
            setDropDownViewResource(R.layout.component_app_spinner_dropdown_item)
        }

        spinner.onItemSelectedListener = this
    }

    fun getSelected() : String? {
        return mItemSelected
    }

    fun setItemSelected(value: String?) {
        if (value != null) {
            val index = mItems.indexOfFirst { value.equals(it, true) }
            if (index != -1) {
                spinner.setSelection(index)
                mItemSelected = value
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        this.mItemSelected = mItems[0]
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        this.mItemSelected = mItems[position]
    }
}*/
