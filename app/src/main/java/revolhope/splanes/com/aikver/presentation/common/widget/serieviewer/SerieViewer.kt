package revolhope.splanes.com.aikver.presentation.common.widget.serieviewer

import android.content.Context
import android.transition.Fade
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.domain.Serie
import kotlinx.android.synthetic.main.component_serie_viewer.view.*

class SerieViewer @JvmOverloads constructor(

    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0

) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.component_serie_viewer, this)
        showShimmer()
    }

    fun updateItems(items: List<Serie>, onSerieClick: (serie: Serie) -> Unit) {
        if (items.isEmpty()) {
            showEmptyState()
        } else {
            hideShimmer()
            hideEmptyState()
            with(recyclerView) {
                layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                adapter = SerieViewerAdapter(items.toMutableList(), onSerieClick)
            }
        }
    }

    fun showShimmer() {
        if (recyclerView.visibility == View.VISIBLE)
            recyclerView.visibility = View.GONE
        if (emptyState.visibility == View.VISIBLE)
            emptyState.visibility = View.GONE

        playTransition()
    }

    private fun hideShimmer() {
        recyclerView.visibility = View.VISIBLE
        shimmerViewContainer.visibility = View.GONE

        playTransition()
    }

    private fun showEmptyState() {
        if (shimmerViewContainer.visibility == View.VISIBLE)
            shimmerViewContainer.visibility = View.GONE
        if (recyclerView.visibility == View.VISIBLE)
            recyclerView.visibility = View.GONE

        emptyState.visibility = View.VISIBLE

        playTransition()
    }

    private fun hideEmptyState() {
        recyclerView.visibility = View.VISIBLE
        emptyState.visibility = View.GONE
        playTransition()
    }

    private fun playTransition() = TransitionManager.beginDelayedTransition(
        this,
        Fade().apply {
            duration = 800
        }
    )
}