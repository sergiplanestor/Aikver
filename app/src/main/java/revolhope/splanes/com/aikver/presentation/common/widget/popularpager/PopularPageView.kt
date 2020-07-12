package revolhope.splanes.com.aikver.presentation.common.widget.popularpager

import android.content.Context
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import android.transition.Fade
import kotlinx.android.synthetic.main.component_popular_pageview.view.arrow
import kotlinx.android.synthetic.main.component_popular_pageview.view.popularContentRecycler
import kotlinx.android.synthetic.main.component_popular_pageview.view.separator
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.presentation.common.invisible
import revolhope.splanes.com.aikver.presentation.common.visible
import revolhope.splanes.com.core.domain.model.content.Content

class PopularPageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleInt: Int = 0
) : ConstraintLayout(context, attrs, defStyleInt) {

    private enum class State {
        EXPANDED,
        COLLAPSED
    }

    private var currentState: State = State.EXPANDED

    init {
        View.inflate(context, R.layout.component_popular_pageview, this)
        initViews()
    }

    private fun initViews() {
        arrow.setOnClickListener {
            if (currentState == State.EXPANDED) collapse() else expand()
        }
    }

    fun addItems(items: List<Content>) {
        popularContentRecycler.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        popularContentRecycler.adapter = PopularPagerAdapter(items.run {
            sortedWith(compareBy { it.popularity })
        })
        PagerSnapHelper().attachToRecyclerView(popularContentRecycler)
        popularContentRecycler.postDelayed({ popularContentRecycler.scrollNext() }, 5000)
    }

    private fun collapse() {
        /* Change current state */
        currentState = State.COLLAPSED

        /* Arrow animation */
        rotateArrow(180f)

        /* Hide view */
        popularContentRecycler.invisible()

        /* Show separator */
        separator.visible()

        /* Transition */
        beginTransition()
    }

    private fun expand() {
        /* Change current state */
        currentState = State.EXPANDED

        /* Arrow animation */
        rotateArrow(0f)

        /* Show view */
        popularContentRecycler.visible()

        /* Hide separator */
        separator.invisible()

        /* Transition */
        beginTransition()
    }

    private fun rotateArrow(angle: Float) {
        arrow.animate().rotation(angle).apply {
            duration = 500
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
    }

    private fun beginTransition() {
        if (parent is ViewGroup) {
            TransitionManager.beginDelayedTransition(parent as ViewGroup, ChangeBounds().apply {
                duration = 500
            })
        }
    }
}