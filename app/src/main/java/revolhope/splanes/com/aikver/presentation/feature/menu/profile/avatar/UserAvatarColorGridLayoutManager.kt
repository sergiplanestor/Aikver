package revolhope.splanes.com.aikver.presentation.feature.menu.profile.avatar

import android.content.Context
import android.util.TypedValue
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import kotlin.math.max


class UserAvatarColorGridLayoutManager @JvmOverloads constructor(
    context: Context,
    orientation: Int = RecyclerView.VERTICAL,
    reverseLayout: Boolean = false
) : GridLayoutManager(context, 1, orientation, reverseLayout) {

    companion object {
        private const val DEFAULT_WIDTH = 48f
    }

    private var columnWidth = 0
    private var isColumnWidthChanged = true
    private var lastWidth = 0
    private var lastHeight = 0

    init {
        setColumnWidth(checkedColumnWidth(context, columnWidth))
    }

    override fun onLayoutChildren(recycler: Recycler, state: RecyclerView.State) {
        if (columnWidth > 0 &&
            width > 0 &&
            height > 0 &&
            (isColumnWidthChanged || lastWidth != width || lastHeight != height)
        ) {
            val totalSpace =
                if (orientation == LinearLayoutManager.VERTICAL) {
                    width - paddingRight - paddingLeft
                } else {
                    height - paddingTop - paddingBottom
                }
            val spanCount = max(1, totalSpace / columnWidth)
            setSpanCount(spanCount)
            isColumnWidthChanged = false
        }
        lastWidth = width
        lastHeight = height
        super.onLayoutChildren(recycler, state)
    }

    private fun checkedColumnWidth(context: Context, columnWidth: Int): Int =
        if (columnWidth <= 0) {
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                DEFAULT_WIDTH,
                context.resources.displayMetrics
            ).toInt()
        } else {
            columnWidth
        }

    private fun setColumnWidth(newColumnWidth: Int) {
        if (newColumnWidth != columnWidth) {
            columnWidth = newColumnWidth
            isColumnWidthChanged = true
        }
    }
}