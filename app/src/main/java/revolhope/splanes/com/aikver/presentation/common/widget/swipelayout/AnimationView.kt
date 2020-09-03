package revolhope.splanes.com.aikver.presentation.common.widget.swipelayout

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View


class AnimationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        private const val TAG = "AnimationView"
    }

    private var PULL_HEIGHT = 0
    private var PULL_DELTA = 0
    private var mWidthOffset = 0f

    private var mAniStatus: AnimatorStatus = AnimatorStatus.PULL_DOWN

    enum class AnimatorStatus {
        PULL_DOWN,
        DRAG_DOWN,
        REL_DRAG,
        SPRING_UP, // rebound to up, the position is less than PULL_HEIGHT
        POP_BALL,
        OUTER_CIR,
        REFRESHING,
        DONE,
        STOP;

        override fun toString(): String {
            return when (this) {
                PULL_DOWN -> "pull down"
                DRAG_DOWN -> "drag down"
                REL_DRAG -> "release drag"
                SPRING_UP -> "spring up"
                POP_BALL -> "pop ball"
                OUTER_CIR -> "outer circle"
                REFRESHING -> "refreshing..."
                DONE -> "done!"
                STOP -> "stop"
            }
        }
    }

    private lateinit var mBackPaint: Paint
    private lateinit var mBallPaint: Paint
    private lateinit var mOutPaint: Paint
    private lateinit var mPath: Path

    private var mRadius = 0
    private var mWidth = 0
    private var mHeight = 0

    private var mRefreshStart = 90
    private var mRefreshStop = 90
    private var TARGET_DEGREE = 270
    private var mIsStart = true
    private var mIsRefreshing = true
    private var mLastHeight = 0
    private val REL_DRAG_DUR: Long = 200
    private val SPRING_DUR: Long = 200
    private var mSprStart: Long = 0
    private var mSprStop: Long = 0

    private var mStart: Long = 0
    private var mStop: Long = 0
    private var mSpriDeta = 0
    private var smallTimes: Int? = null

    init {
        initView(context)
    }

    private fun initView(context: Context) {
        PULL_HEIGHT = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            100f,
            context.resources.displayMetrics
        ).toInt()

        PULL_DELTA = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            50f,
            context.resources.displayMetrics
        ).toInt()

        mWidthOffset = 0.5f

        mBackPaint = Paint()
        mBackPaint.isAntiAlias = true
        mBackPaint.style = Paint.Style.FILL
        mBackPaint.color = 0xff8b90af.toInt()

        mBallPaint = Paint()
        mBallPaint.isAntiAlias = true
        mBallPaint.color = 0xffffffff.toInt()
        mBallPaint.style = Paint.Style.FILL

        mOutPaint = Paint()
        mOutPaint.isAntiAlias = true
        mOutPaint.color = 0xffffffff.toInt()
        mOutPaint.style = Paint.Style.STROKE
        mOutPaint.strokeWidth = 5f
        mPath = Path()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val height = MeasureSpec.getSize(heightMeasureSpec)
        super.onMeasure(
            widthMeasureSpec,
            if (height > PULL_DELTA + PULL_HEIGHT) {
                MeasureSpec.makeMeasureSpec(
                    PULL_DELTA + PULL_HEIGHT,
                    MeasureSpec.getMode(heightMeasureSpec)
                )
            } else {
                heightMeasureSpec
            }
        )
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            mRadius = height / (smallTimes ?: 6)
            mWidth = width
            mHeight = height
            if (mHeight < PULL_HEIGHT) {
                mAniStatus = AnimatorStatus.PULL_DOWN
            }
            if (mAniStatus == AnimatorStatus.PULL_DOWN && mHeight >= PULL_HEIGHT) {
                mAniStatus = AnimatorStatus.DRAG_DOWN
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        when (mAniStatus) {
            AnimatorStatus.PULL_DOWN -> canvas.drawRect(
                0f,
                0f,
                mWidth.toFloat(),
                mHeight.toFloat(),
                mBackPaint
            )
            AnimatorStatus.REL_DRAG, AnimatorStatus.DRAG_DOWN -> drawDrag(canvas)
            AnimatorStatus.SPRING_UP -> {
                drawSpring(canvas, getSpringDelta())
                invalidate()
            }
            AnimatorStatus.POP_BALL -> {
                drawPopBall(canvas)
                invalidate()
            }
            AnimatorStatus.OUTER_CIR -> {
                drawOutCir(canvas)
                invalidate()
            }
            AnimatorStatus.REFRESHING -> {
                drawRefreshing(canvas)
                invalidate()
            }
            AnimatorStatus.DONE -> {
                drawDone(canvas)
                invalidate()
            }
            AnimatorStatus.STOP -> drawDone(canvas)
        }
        if (mAniStatus === AnimatorStatus.REL_DRAG) {
            val params = layoutParams
            var height: Int
            // NOTICE: If the height equals mLastHeight, then the requestLayout() will not work correctly
            do {
                height = getRelHeight()
            } while (height == mLastHeight && getRelRatio() != 1f)
            mLastHeight = height
            params.height = PULL_HEIGHT + height
            requestLayout()
        }
    }

    private fun drawDrag(canvas: Canvas) {
        canvas.drawRect(0f, 0f, mWidth.toFloat(), PULL_HEIGHT.toFloat(), mBackPaint)
        mPath.reset()
        mPath.moveTo(0f, PULL_HEIGHT.toFloat())
        mPath.quadTo(
            mWidthOffset * mWidth, PULL_HEIGHT + (mHeight - PULL_HEIGHT) * 2.toFloat(),
            mWidth.toFloat(), PULL_HEIGHT.toFloat()
        )
        canvas.drawPath(mPath, mBackPaint)
    }

    private fun drawSpring(canvas: Canvas, springDelta: Int) {
        mPath.reset()
        mPath.moveTo(0f, 0f)
        mPath.lineTo(0f, PULL_HEIGHT.toFloat())
        mPath.quadTo(
            mWidth / 2.toFloat(), PULL_HEIGHT - springDelta.toFloat(),
            mWidth.toFloat(), PULL_HEIGHT.toFloat()
        )
        mPath.lineTo(mWidth.toFloat(), 0f)
        canvas.drawPath(mPath, mBackPaint)
        val curH = PULL_HEIGHT - springDelta / 2
        if (curH > PULL_HEIGHT - PULL_DELTA / 2) {
            val leftX = (mWidth / 2 - 2 * mRadius + getSprRatio() * mRadius).toInt()
            mPath.reset()
            mPath.moveTo(leftX.toFloat(), curH.toFloat())
            mPath.quadTo(
                mWidth / 2.toFloat(), curH - mRadius * getSprRatio() * 2,
                mWidth - leftX.toFloat(), curH.toFloat()
            )
            canvas.drawPath(mPath, mBallPaint)
        } else {
            canvas.drawArc(
                RectF(
                    (mWidth / 2 - mRadius).toFloat(),
                    (curH - mRadius).toFloat(),
                    (mWidth / 2 + mRadius).toFloat(),
                    (curH + mRadius).toFloat()
                ), 180f, 180f, true, mBallPaint
            )
        }
    }

    private fun drawPopBall(canvas: Canvas) {
        mPath.reset()
        mPath.moveTo(0f, 0f)
        mPath.lineTo(0f, PULL_HEIGHT.toFloat())
        mPath.quadTo(
            mWidth / 2.toFloat(), PULL_HEIGHT - PULL_DELTA.toFloat(),
            mWidth.toFloat(), PULL_HEIGHT.toFloat()
        )
        mPath.lineTo(mWidth.toFloat(), 0f)
        canvas.drawPath(mPath, mBackPaint)
        val cirCentStart = PULL_HEIGHT - PULL_DELTA / 2
        val cirCenY = (cirCentStart - mRadius * 2 * getPopRatio()).toInt()
        canvas.drawArc(
            RectF(
                (mWidth / 2 - mRadius).toFloat(),
                (cirCenY - mRadius).toFloat(),
                (mWidth / 2 + mRadius).toFloat(),
                (cirCenY + mRadius).toFloat()
            ), 180f, 360f, true, mBallPaint
        )
        if (getPopRatio() < 1) {
            drawTail(canvas, cirCenY, cirCentStart + 1, getPopRatio())
        } else {
            canvas.drawCircle(
                mWidth / 2.toFloat(),
                cirCenY.toFloat(),
                mRadius.toFloat(),
                mBallPaint
            )
        }
    }

    private fun drawTail(canvas: Canvas, centerY: Int, bottom: Int, fraction: Float) {
        val bezier1w = (mWidth / 2 + mRadius * 3 / 4 * (1 - fraction)).toInt()
        val start = PointF((mWidth / 2 + mRadius).toFloat(), centerY.toFloat())
        val bezier1 = PointF(bezier1w.toFloat(), bottom.toFloat())
        val bezier2 = PointF((bezier1w + mRadius / 2).toFloat(), bottom.toFloat())
        mPath.reset()
        mPath.moveTo(start.x, start.y)
        mPath.quadTo(
            bezier1.x, bezier1.y,
            bezier2.x, bezier2.y
        )
        mPath.lineTo(mWidth - bezier2.x, bezier2.y)
        mPath.quadTo(
            mWidth - bezier1.x, bezier1.y,
            mWidth - start.x, start.y
        )
        canvas.drawPath(mPath, mBallPaint)
    }

    private fun drawOutCir(canvas: Canvas) {
        mPath.reset()
        mPath.moveTo(0f, 0f)
        mPath.lineTo(0f, PULL_HEIGHT.toFloat())
        mPath.quadTo(
            mWidth / 2.toFloat(), PULL_HEIGHT - (1 - getOutRatio()) * PULL_DELTA,
            mWidth.toFloat(), PULL_HEIGHT.toFloat()
        )
        mPath.lineTo(mWidth.toFloat(), 0f)
        canvas.drawPath(mPath, mBackPaint)
        val innerY = PULL_HEIGHT - PULL_DELTA / 2 - mRadius * 2
        canvas.drawCircle(mWidth / 2.toFloat(), innerY.toFloat(), mRadius.toFloat(), mBallPaint)
    }

    private fun drawRefreshing(canvas: Canvas) {
        canvas.drawRect(0f, 0f, mWidth.toFloat(), mHeight.toFloat(), mBackPaint)
        val innerY = PULL_HEIGHT - PULL_DELTA / 2 - mRadius * 2
        canvas.drawCircle(mWidth / 2.toFloat(), innerY.toFloat(), mRadius.toFloat(), mBallPaint)
        val outerR = mRadius + 10
        mRefreshStart += if (mIsStart) 3 else 10
        mRefreshStop += if (mIsStart) 10 else 3
        mRefreshStart = mRefreshStart % 360
        mRefreshStop = mRefreshStop % 360
        var swipe = mRefreshStop - mRefreshStart
        swipe = if (swipe < 0) swipe + 360 else swipe
        canvas.drawArc(
            RectF(
                (mWidth / 2 - outerR).toFloat(),
                (innerY - outerR).toFloat(),
                (mWidth / 2 + outerR).toFloat(),
                (innerY + outerR).toFloat()
            ),
            mRefreshStart.toFloat(), swipe.toFloat(), false, mOutPaint
        )
        if (swipe >= TARGET_DEGREE) {
            mIsStart = false
        } else if (swipe <= 10) {
            mIsStart = true
        }
        if (!mIsRefreshing) {
            applyDone()
        }
    }

    // stop refreshing
    fun setRefreshing(isFresh: Boolean) {
        mIsRefreshing = isFresh
    }

    private fun drawDone(canvas: Canvas) {
        val beforeColor = mOutPaint.color
        if (getDoneRatio() < 0.3) {
            canvas.drawRect(0f, 0f, mWidth.toFloat(), mHeight.toFloat(), mBackPaint)
            val innerY = PULL_HEIGHT - PULL_DELTA / 2 - mRadius * 2
            canvas.drawCircle(mWidth / 2.toFloat(), innerY.toFloat(), mRadius.toFloat(), mBallPaint)
            val outerR = (mRadius + 10 + 10 * getDoneRatio() / 0.3f).toInt()
            val afterColor: Int = Color.argb(
                (0xff * (1 - getDoneRatio() / 0.3f)).toInt(), Color.red(beforeColor),
                Color.green(beforeColor), Color.blue(beforeColor)
            )
            mOutPaint.color = afterColor
            canvas.drawArc(
                RectF(
                    (mWidth / 2 - outerR).toFloat(),
                    (innerY - outerR).toFloat(),
                    (mWidth / 2 + outerR).toFloat(),
                    (innerY + outerR).toFloat()
                ), 0f, 360f, false, mOutPaint
            )
        }
        mOutPaint.color = beforeColor
        if (getDoneRatio() >= 0.3 && getDoneRatio() < 0.7) {
            canvas.drawRect(0f, 0f, mWidth.toFloat(), mHeight.toFloat(), mBackPaint)
            val fraction: Float = (getDoneRatio() - 0.3f) / 0.4f
            val startCentY = PULL_HEIGHT - PULL_DELTA / 2 - mRadius * 2
            val curCentY = (startCentY + (PULL_DELTA / 2 + mRadius * 2) * fraction).toInt()
            canvas.drawCircle(
                mWidth / 2.toFloat(),
                curCentY.toFloat(),
                mRadius.toFloat(),
                mBallPaint
            )
            if (curCentY >= PULL_HEIGHT - mRadius * 2) {
                drawTail(canvas, curCentY, PULL_HEIGHT, 1 - fraction)
            }
        }
        if (getDoneRatio() in 0.7..1.0) {
            val fraction: Float = (getDoneRatio() - 0.7f) / 0.3f
            canvas.drawRect(0f, 0f, mWidth.toFloat(), mHeight.toFloat(), mBackPaint)
            val leftX = (mWidth / 2 - mRadius - 2 * mRadius * fraction).toInt()
            mPath.reset()
            mPath.moveTo(leftX.toFloat(), PULL_HEIGHT.toFloat())
            mPath.quadTo(
                mWidth / 2.toFloat(), PULL_HEIGHT - mRadius * (1 - fraction),
                mWidth - leftX.toFloat(), PULL_HEIGHT.toFloat()
            )
            canvas.drawPath(mPath, mBallPaint)
        }
    }

    private fun getRelHeight(): Int {
        return (mSpriDeta * (1 - getRelRatio())).toInt()
    }

    private fun getSpringDelta(): Int {
        return (PULL_DELTA * getSprRatio()).toInt()
    }

    fun releaseDrag() {
        mStart = System.currentTimeMillis()
        mStop = mStart + REL_DRAG_DUR
        mAniStatus = AnimatorStatus.REL_DRAG
        mSpriDeta = mHeight - PULL_HEIGHT
        requestLayout()
    }

    private fun getRelRatio(): Float {
        if (System.currentTimeMillis() >= mStop) {
            springUp()
            return 1f
        }
        val ratio = (System.currentTimeMillis() - mStart) / REL_DRAG_DUR.toFloat()
        return ratio.coerceAtMost(1f)
    }

    private fun springUp() {
        mSprStart = System.currentTimeMillis()
        mSprStop = mSprStart + SPRING_DUR
        mAniStatus = AnimatorStatus.SPRING_UP
        invalidate()
    }


    private fun getSprRatio(): Float {
        if (System.currentTimeMillis() >= mSprStop) {
            popBall()
            return 1f
        }
        val ratio = (System.currentTimeMillis() - mSprStart) / SPRING_DUR.toFloat()
        return 1f.coerceAtMost(ratio)
    }

    private val POP_BALL_DUR: Long = 300
    private var mPopStart: Long = 0
    private var mPopStop: Long = 0

    private fun popBall() {
        mPopStart = System.currentTimeMillis()
        mPopStop = mPopStart + POP_BALL_DUR
        mAniStatus = AnimatorStatus.POP_BALL
        invalidate()
    }

    private fun getPopRatio(): Float {
        if (System.currentTimeMillis() >= mPopStop) {
            startOutCir()
            return 1f
        }
        val ratio = (System.currentTimeMillis() - mPopStart) / POP_BALL_DUR.toFloat()
        return ratio.coerceAtMost(1f)
    }

    private val OUTER_DUR: Long = 200
    private var mOutStart: Long = 0
    private var mOutStop: Long = 0

    private fun startOutCir() {
        mOutStart = System.currentTimeMillis()
        mOutStop = mOutStart + OUTER_DUR
        mAniStatus = AnimatorStatus.OUTER_CIR
        mRefreshStart = 90
        mRefreshStop = 90
        TARGET_DEGREE = 270
        mIsStart = true
        mIsRefreshing = true
        invalidate()
    }

    private fun getOutRatio(): Float {
        if (System.currentTimeMillis() >= mOutStop) {
            mAniStatus = AnimatorStatus.REFRESHING
            mIsRefreshing = true
            return 1f
        }
        val ratio = (System.currentTimeMillis() - mOutStart) / OUTER_DUR.toFloat()
        return ratio.coerceAtMost(1f)
    }

    private val DONE_DUR: Long = 1000
    private var mDoneStart: Long = 0
    private var mDoneStop: Long = 0

    private fun applyDone() {
        mDoneStart = System.currentTimeMillis()
        mDoneStop = mDoneStart + DONE_DUR
        mAniStatus = AnimatorStatus.DONE
    }

    private fun getDoneRatio(): Float {
        if (System.currentTimeMillis() >= mDoneStop) {
            mAniStatus = AnimatorStatus.STOP
            if (onViewAniDone != null) {
                onViewAniDone!!.viewAniDone()
            }
            return 1f
        }
        val ratio = (System.currentTimeMillis() - mDoneStart) / DONE_DUR.toFloat()
        return ratio.coerceAtMost(1f)
    }


    private var onViewAniDone: OnViewAniDone? = null

    fun setOnViewAniDone(onViewAniDone: OnViewAniDone?) {
        this.onViewAniDone = onViewAniDone
    }

    interface OnViewAniDone {
        fun viewAniDone()
    }

    fun setAniBackColor(color: Int) {
        mBackPaint.color = color
    }

    fun setAniForeColor(color: Int) {
        mBallPaint.color = color
        mOutPaint.color = color
        setBackgroundColor(color)
    }

    // the height of view is smallTimes times of circle radius
    fun setRadius(smallTimes: Int) {
        this.smallTimes = smallTimes
        mRadius = mHeight / smallTimes
    }
}