package revolhope.splanes.com.aikver.presentation.common

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.view.View
import android.view.ViewAnimationUtils
import androidx.core.animation.addListener
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import kotlin.math.hypot


object AnimUtils {

    private const val DURATION = 750L

    fun playCircularRevealIn(
        view: View,
        model: AnimCircularRevealModel,
        colors: Pair<Int, Int>? = null
    ) {
        execCircularReveal(
            view = view,
            model = model,
            startRadius = 0f,
            endRadius = hypot(model.width.toDouble(), model.height.toDouble()).toFloat(),
            colors = colors,
            isViewChanging = true
        )
    }

    fun playCircularRevealOut(
        view: View,
        model: AnimCircularRevealModel,
        colors: Pair<Int, Int>? = null,
        onFinish: () -> Unit
    ) {
        execCircularReveal(
            view = view,
            model = model,
            startRadius = hypot(model.width.toDouble(), model.height.toDouble()).toFloat(),
            endRadius = 0f,
            colors = colors,
            onFinish = onFinish
        )
    }

    private fun execCircularReveal(
        view: View,
        model: AnimCircularRevealModel,
        startRadius: Float,
        endRadius: Float,
        colors: Pair<Int, Int>? = null,
        isViewChanging: Boolean = false,
        onFinish: (() -> Unit)? = null
    ) {
        if (isViewChanging) {
            view.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
                override fun onLayoutChange(
                    v: View,
                    left: Int,
                    top: Int,
                    right: Int,
                    bottom: Int,
                    oldLeft: Int,
                    oldTop: Int,
                    oldRight: Int,
                    oldBottom: Int
                ) {
                    v.removeOnLayoutChangeListener(this)
                    performCircularAnim(view, model, startRadius, endRadius, onFinish, colors)
                }
            })
        } else {
            performCircularAnim(view, model, startRadius, endRadius, onFinish, colors)
        }
    }

    private fun performCircularAnim(
        view: View,
        model: AnimCircularRevealModel,
        startRadius: Float,
        endRadius: Float,
        onFinish: (() -> Unit)?,
        colors: Pair<Int, Int>?
    ) {
        ViewAnimationUtils.createCircularReveal(
            view,
            model.centerX,
            model.centerY,
            startRadius,
            endRadius
        ).apply {
            this.duration = DURATION
            this.interpolator = FastOutSlowInInterpolator()
            this.addListener(onEnd = { onFinish?.invoke() })
        }.start()

        if (colors != null) playColorAnimation(view, colors)
    }

    private fun playColorAnimation(view: View, colors: Pair<Int, Int>) {
        ValueAnimator().apply {
            setIntValues(colors.first, colors.second)
            setEvaluator(ArgbEvaluator())
            addUpdateListener {
                view.setBackgroundColor(it.animatedValue as Int)
            }
            this.duration = DURATION
        }.start()
    }

    data class AnimCircularRevealModel(
        val centerX: Int,
        val centerY: Int,
        val width: Int,
        val height: Int
    )
}