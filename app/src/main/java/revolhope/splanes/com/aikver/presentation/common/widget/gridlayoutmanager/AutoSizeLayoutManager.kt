package revolhope.splanes.com.aikver.presentation.common.widget.gridlayoutmanager

import android.content.Context
import android.util.TypedValue
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.max

class AutoSizeLayoutManager @JvmOverloads constructor(
    context: Context,
    private val defaultWidth: Float,
    orientation: Int = RecyclerView.VERTICAL,
    reverseLayout: Boolean = false
) : GridLayoutManager(context, 1, orientation, reverseLayout) {

    private var columnWidth = 0
    private var calculatedSpanCount: Int = 1

    init {
        setColumnWidth(checkedColumnWidth(context, columnWidth))
    }

    override fun onMeasure(
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State,
        widthSpec: Int,
        heightSpec: Int
    ) {
        if (shouldAutoSize()) {
            calculatedSpanCount = max(1, (width - paddingRight - paddingLeft) / columnWidth)
            this.spanCount = calculatedSpanCount
        }
        super.onMeasure(recycler, state, widthSpec, heightSpec)
    }

    private fun shouldAutoSize() =
        columnWidth > 0 && width > 0 && (spanCount == 1 || spanCount != calculatedSpanCount)

    private fun checkedColumnWidth(context: Context, columnWidth: Int): Int =
        if (columnWidth <= 0) {
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                defaultWidth,
                context.resources.displayMetrics
            ).toInt()
        } else {
            columnWidth
        }

    private fun setColumnWidth(newColumnWidth: Int) {
        if (newColumnWidth != columnWidth) {
            columnWidth = newColumnWidth
        }
    }
}