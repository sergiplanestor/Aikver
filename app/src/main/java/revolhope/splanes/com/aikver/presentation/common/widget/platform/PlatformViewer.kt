package revolhope.splanes.com.aikver.presentation.common.widget.platform
/*

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import revolhope.splanes.com.aikver.R
import bemobile.splanes.com.core.domain.Platform
import kotlinx.android.synthetic.main.component_platform_viewer.view.*
import revolhope.splanes.com.aikver.presentation.common.dpToPx

class PlatformViewer @JvmOverloads constructor(

    context: Context,
    private val attrs: AttributeSet? = null,
    private val defStyleAttr: Int = 0

) : LinearLayout(context, attrs, defStyleAttr), OnPlatformClick {

    private var selectedPlatform: bemobile.splanes.com.core.domain.Platform? = null
    private var imageDimension: Float? = null

    init {
        View.inflate(context, R.layout.component_platform_viewer, this)
        if (attrs != null) parseAttrs(attrs)
        initialize()
    }

    private fun initialize() {
        recycler.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        recycler.adapter = PlatformAdapter(
            context = context,
            dimension = imageDimension ?: dpToPx(context, 60).toFloat(),
            onPlatformClick = this
        )
    }

    private fun parseAttrs(attrs: AttributeSet) {
        val ta = context.obtainStyledAttributes(
            attrs,
            R.styleable.PlatformViewer,
            defStyleAttr,
            0
        )

        ta.getBoolean(R.styleable.PlatformViewer_showTitle, true)
            .run { shouldShowTitle(this) }
        imageDimension = ta.getDimension(
            R.styleable.PlatformViewer_imageSize,
            dpToPx(context, 60).toFloat()
        )

        ta.recycle()
    }

    private fun shouldShowTitle(showTitle: Boolean) {
        title.visibility = if (showTitle) View.VISIBLE else View.GONE
    }

    override fun onPlatformClick(platform: bemobile.splanes.com.core.domain.Platform) {
        selectedPlatform = platform
    }

    fun getSelectedPlatform(): bemobile.splanes.com.core.domain.Platform? {
        return selectedPlatform
    }

    fun setSelectedPlatform(platform: bemobile.splanes.com.core.domain.Platform?) {
        if (platform != null) {
            (recycler.adapter as PlatformAdapter).setItemSelected(platform.name)
        }
    }
}*/
