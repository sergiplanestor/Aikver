package revolhope.splanes.com.aikver.presentation.common.widget.popularpager

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
//import kotlinx.android.synthetic.main.component_popular_pageview.view.popularContentCardView
import kotlinx.android.synthetic.main.component_popular_pageview.view.popularContentIcon
//import kotlinx.android.synthetic.main.component_popular_pageview.view.popularContentLayout
//import kotlinx.android.synthetic.main.component_popular_pageview.view.popularContentMinify
import kotlinx.android.synthetic.main.component_popular_pageview.view.popularContentRecycler
import kotlinx.android.synthetic.main.component_popular_pageview.view.popularContentTitle
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
        /*popularContentMinify.setOnClickListener { if (currentState == State.EXPANDED) collapse() }
        popularContentIcon.setOnClickListener { if (currentState == State.COLLAPSED) expand() }*/
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

    /*private fun expand() {
        *//* Change current state *//*
        currentState = State.EXPANDED
        *//* Set views to gone *//*
        popularContentRecycler.visible()
        popularContentMinify.visible()
        popularContentTitle.visible()
        *//* Change image *//*
        popularContentIcon.setImageResource(R.drawable.round_star_24)
        *//* Change constraints *//*
        ConstraintSet().apply {
            clone(popularContentLayout)
            connect(
                popularContentIcon.id,
                ConstraintSet.START,
                ConstraintSet.PARENT_ID,
                ConstraintSet.START
            )
            connect(
                popularContentIcon.id,
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.TOP
            )
            clear(popularContentIcon.id, ConstraintSet.END)
            clear(popularContentIcon.id, ConstraintSet.BOTTOM)
            applyTo(popularContentLayout)
        }
        ConstraintSet().apply {
            clone(parent as ConstraintLayout)
            connect(
                id,
                ConstraintSet.START,
                ConstraintSet.PARENT_ID,
                ConstraintSet.START
            )
            connect(
                id,
                ConstraintSet.END,
                ConstraintSet.PARENT_ID,
                ConstraintSet.END
            )
            connect(
                id,
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.TOP
            )
            applyTo(parent as ConstraintLayout)
        }
        *//* Change width params *//*
        popularContentCardView.layoutParams = popularContentCardView.layoutParams.apply {
            width = LayoutParams.MATCH_PARENT
        }
        *//* Animate *//*
        TransitionManager.beginDelayedTransition(popularContentCardView, AutoTransition().apply {
            duration = 500
        })
    }

    private fun collapse() {
        *//* Change current state *//*
        currentState = State.COLLAPSED
        *//* Set views to gone *//*
        popularContentRecycler.invisible()
        popularContentMinify.invisible()
        popularContentTitle.invisible()
        *//* Change image *//*
        popularContentIcon.setImageResource(R.drawable.round_star_border_24)
        *//* Change constraints *//*
        ConstraintSet().apply {
            clone(popularContentLayout)
            connect(
                popularContentIcon.id,
                ConstraintSet.START,
                ConstraintSet.PARENT_ID,
                ConstraintSet.START
            )
            connect(
                popularContentIcon.id,
                ConstraintSet.END,
                ConstraintSet.PARENT_ID,
                ConstraintSet.END
            )
            connect(
                popularContentIcon.id,
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.TOP
            )
            connect(
                popularContentIcon.id,
                ConstraintSet.BOTTOM,
                ConstraintSet.PARENT_ID,
                ConstraintSet.BOTTOM
            )
            applyTo(popularContentLayout)
        }
        ConstraintSet().apply {
            clone(parent as ConstraintLayout)
            clear(popularContentCardView.id, ConstraintSet.START)
            connect(
                id,
                ConstraintSet.END,
                ConstraintSet.PARENT_ID,
                ConstraintSet.END
            )
            connect(
                id,
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.TOP
            )
            connect(
                id,
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.TOP
            )
            connect(
                id,
                ConstraintSet.BOTTOM,
                ConstraintSet.PARENT_ID,
                ConstraintSet.BOTTOM
            )
            applyTo(parent as ConstraintLayout)
        }
        *//* Change width params *//*
        popularContentCardView.layoutParams = popularContentCardView.layoutParams.apply {
            width = LayoutParams.WRAP_CONTENT
        }
        *//* Animate *//*
        TransitionManager.beginDelayedTransition(popularContentCardView, AutoTransition().apply {
            duration = 500
        })
    }*/
}