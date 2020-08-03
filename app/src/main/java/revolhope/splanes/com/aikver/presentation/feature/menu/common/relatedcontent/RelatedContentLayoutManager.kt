package revolhope.splanes.com.aikver.presentation.feature.menu.common.relatedcontent

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RelatedContentLayoutManager(
    context: Context,
    private val callback: (() -> Unit)? = null
): LinearLayoutManager(context, RecyclerView.HORIZONTAL, false) {

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        if (isLastItemVisible()) callback?.invoke()
    }

    private fun isLastItemVisible() = findLastVisibleItemPosition() == itemCount.minus(1)

}