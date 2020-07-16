package revolhope.splanes.com.aikver.presentation.common.widget.contentpager

import android.animation.Animator
import android.content.Context
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.component_popular_pageview.view.arrow
import kotlinx.android.synthetic.main.component_popular_pageview.view.popularContentRecycler
import kotlinx.android.synthetic.main.component_popular_pageview.view.separator
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.presentation.common.invisible
import revolhope.splanes.com.aikver.presentation.common.visible
import revolhope.splanes.com.core.domain.model.content.Content

class ContentPagerView @JvmOverloads constructor(
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

    private fun initViews() = arrow.setOnClickListener { changeState() }

    fun addItems(items: List<Content>) {
        with(popularContentRecycler) {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = ContentPagerAdapter(items.run { sortedWith(compareBy { it.popularity }) })
            PagerSnapHelper().attachToRecyclerView(this)
            resumeAutoScroll()
        }
    }

    private fun changeState() {
        rotateArrow(if (currentState == State.EXPANDED) 180f else 0f)
        currentState = when (currentState) {
            State.EXPANDED -> State.COLLAPSED
            State.COLLAPSED -> State.EXPANDED
        }
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
        popularContentRecycler.animate().apply {
            duration = 650
            interpolator = AccelerateDecelerateInterpolator()
            alpha(if (currentState == State.EXPANDED) 1f else 0f)
            setListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(anim: Animator?) { /* Nothing to do here */
                }

                override fun onAnimationEnd(anim: Animator?) {
                    if (currentState == State.COLLAPSED) {
                        popularContentRecycler.invisible()
                        separator.alpha = 0f
                        separator.visible()
                        separator.animate().apply {
                            duration = 300
                            alpha(1f)
                            start()
                        }
                    }
                    TransitionManager.beginDelayedTransition(parent as ViewGroup)
                }

                override fun onAnimationStart(anim: Animator?) {
                    if (currentState == State.EXPANDED) {
                        popularContentRecycler.alpha = 0f
                        popularContentRecycler.visible()
                        separator.invisible()
                    }
                }

                override fun onAnimationCancel(anim: Animator?) { /* Nothing to do here */
                }
            })
            start()
        }
    }
}