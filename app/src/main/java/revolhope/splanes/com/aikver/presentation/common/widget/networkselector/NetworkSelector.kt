package revolhope.splanes.com.aikver.presentation.common.widget.networkselector

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.component_network_selector.view.recycler
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.presentation.common.dpToPx
import revolhope.splanes.com.aikver.presentation.common.widget.gridlayoutmanager.AutoSizeLayoutManager
import revolhope.splanes.com.core.domain.model.content.Network

class NetworkSelector @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attributeSet, defStyle) {

    private val adapter: NetworkAdapter = NetworkAdapter()

    init {
        View.inflate(context, R.layout.component_network_selector, this)
        recycler.adapter = adapter
        recycler.layoutManager = AutoSizeLayoutManager(
            context,
            60f
        )
    }

    fun getSelected(): Network = adapter.getSelected()
}