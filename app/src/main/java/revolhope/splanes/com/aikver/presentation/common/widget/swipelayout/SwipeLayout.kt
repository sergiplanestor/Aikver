package revolhope.splanes.com.aikver.presentation.common.widget.swipelayout

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import revolhope.splanes.com.aikver.R


class SwipeLayout @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        const val BACK_TOP_DUR: Long = 600
        const val REL_DRAG_DUR: Long = 200
    }

    private var mHeaderBackColor = 0xff8b90af.toInt()
    private var mHeaderForeColor = 0xffffffff.toInt()
    private var mHeaderCircleSmaller = 6


    private var mPullHeight = 0f
    private var mHeaderHeight = 0f
    private var mChildView: View? = null
    private var mHeader: AnimationView? = null

    private var mIsRefreshing = false

    private var mTouchStartY = 0f

    private var mTouchCurY = 0f

    private lateinit var mUpBackAnimator: ValueAnimator
    private lateinit var mUpTopAnimator: ValueAnimator

    private val decelerateInterpolator = DecelerateInterpolator(10f)

    init {
        if (childCount > 1) {
            throw RuntimeException("you can only attach one child")
        }
        attrs?.let { setAttrs(attrs) }
        mPullHeight = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            150f,
            context.resources.displayMetrics
        )
        mHeaderHeight = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            100f,
            context.resources.displayMetrics
        )
        post {
            mChildView = getChildAt(0)
            addHeaderView()
        }
    }

    private fun setAttrs(attrs: AttributeSet) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.SwipeLayout)
        mHeaderBackColor = a.getColor(R.styleable.SwipeLayout_AniBackColor, mHeaderBackColor)
        mHeaderForeColor = a.getColor(R.styleable.SwipeLayout_AniForeColor, mHeaderForeColor)
        mHeaderCircleSmaller = a.getInt(R.styleable.SwipeLayout_CircleSmaller, mHeaderCircleSmaller)
        a.recycle()
    }

    private fun addHeaderView() {
        mHeader = AnimationView(context)
        val params = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0)
        params.gravity = Gravity.TOP
        mHeader?.layoutParams = params
        mHeader?.run(::addViewInternal)
        mHeader?.setAniBackColor(mHeaderBackColor)
        mHeader?.setAniForeColor(mHeaderForeColor)
        mHeader?.setRadius(mHeaderCircleSmaller)
        setUpChildAnimation()
    }

    private fun setUpChildAnimation() {
        if (mChildView == null) {
            return
        }
        mUpBackAnimator = ValueAnimator.ofFloat(mPullHeight, mHeaderHeight)
        mUpBackAnimator.addUpdateListener { animation ->
            val value = animation.animatedValue as Float
            if (mChildView != null) {
                mChildView!!.translationY = value
            }
        }
        mUpBackAnimator.duration = REL_DRAG_DUR
        mUpTopAnimator = ValueAnimator.ofFloat(mHeaderHeight, 0f)
        mUpTopAnimator.addUpdateListener { animation ->
            var value = animation.animatedValue as Float
            value *= decelerateInterpolator.getInterpolation(value / mHeaderHeight)
            if (mChildView != null) {
                mChildView!!.translationY = value
            }
            mHeader?.layoutParams?.height = value.toInt()
            mHeader?.requestLayout()
        }
        mUpTopAnimator.duration = BACK_TOP_DUR
        mHeader?.setOnViewAniDone(object : AnimationView.OnViewAniDone {
            override fun viewAniDone() {
                mUpTopAnimator.start()
            }
        })
    }

    private fun addViewInternal(child: View) {
        super.addView(child)
    }

    override fun addView(child: View) {
        if (childCount >= 1) {
            throw java.lang.RuntimeException("you can only attach one child")
        }
        mChildView = child
        super.addView(child)
        setUpChildAnimation()
    }

    private fun canChildScrollUp(): Boolean {
        return if (mChildView == null) {
            false
        } else ViewCompat.canScrollVertically(mChildView, -1)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (mIsRefreshing) {
            return true
        }
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                mTouchStartY = ev.y
                mTouchCurY = mTouchStartY
            }
            MotionEvent.ACTION_MOVE -> {
                val curY = ev.y
                val dy = curY - mTouchStartY
                if (dy > 0 && !canChildScrollUp()) {
                    return true
                }
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (mIsRefreshing) {
            super.onTouchEvent(event)
        } else when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                mTouchCurY = event.y
                var dy = mTouchCurY - mTouchStartY
                dy = (mPullHeight * 2).coerceAtMost(dy)
                dy = 0f.coerceAtLeast(dy)
                if (mChildView != null) {
                    val offsetY =
                        decelerateInterpolator.getInterpolation(dy / 2 / mPullHeight) * dy / 2
                    mChildView!!.translationY = offsetY
                    mHeader?.layoutParams?.height = offsetY.toInt()
                    mHeader?.requestLayout()
                }
                false
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                if (mChildView != null) {
                    if (mChildView!!.translationY >= mHeaderHeight) {
                        mUpBackAnimator.start()
                        mHeader?.releaseDrag()
                        mIsRefreshing = true
                        if (onCircleRefreshListener != null) {
                            onCircleRefreshListener!!.refreshing()
                        }
                    } else {
                        val height = mChildView!!.translationY
                        val backTopAni = ValueAnimator.ofFloat(height, 0f)
                        backTopAni.addUpdateListener { animation ->
                            var value = animation.animatedValue as Float
                            value *= decelerateInterpolator.getInterpolation(value / mHeaderHeight)
                            if (mChildView != null) {
                                mChildView!!.translationY = value
                            }
                            mHeader?.layoutParams?.height = value.toInt()
                            mHeader?.requestLayout()
                        }
                        backTopAni.duration = (height * BACK_TOP_DUR / mHeaderHeight).toLong()
                        backTopAni.start()
                    }
                }
                false
            }
            else -> super.onTouchEvent(event)
        }
    }

    fun finishRefreshing() {
        if (onCircleRefreshListener != null) {
            onCircleRefreshListener!!.completeRefresh()
        }
        mIsRefreshing = false
        mHeader?.setRefreshing(false)
    }

    private var onCircleRefreshListener: OnCircleRefreshListener? = null

    fun setOnRefreshListener(onCircleRefreshListener: OnCircleRefreshListener?) {
        this.onCircleRefreshListener = onCircleRefreshListener
    }

    interface OnCircleRefreshListener {
        fun completeRefresh()
        fun refreshing()
    }
}