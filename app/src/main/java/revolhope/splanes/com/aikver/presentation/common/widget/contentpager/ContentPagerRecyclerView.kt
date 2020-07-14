package revolhope.splanes.com.aikver.presentation.common.widget.contentpager

import android.content.Context
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import java.lang.ref.WeakReference

class ContentPagerRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {
    private val scrollHandler by lazy {
        ScrollHandler(this)
    }

    private val delayMillis: Long = 7500

    private fun createScroller(position: Int) = object : LinearSmoothScroller(context) {
        override fun getHorizontalSnapPreference(): Int = SNAP_TO_END
    }.apply { targetPosition = position }

    override fun dispatchTouchEvent(e: MotionEvent?): Boolean {
        when (e?.action) {
            MotionEvent.ACTION_UP -> resumeAutoScroll()
            MotionEvent.ACTION_DOWN -> pauseAutoScroll()
        }
        parent.requestDisallowInterceptTouchEvent(true)

        return super.dispatchTouchEvent(e)
    }

    private fun pauseAutoScroll() {
        scrollHandler.removeMessages(WHAT_SCROLL)
    }

    private fun resumeAutoScroll() {
        scrollHandler.removeMessages(WHAT_SCROLL)
        scrollHandler.sendEmptyMessageDelayed(WHAT_SCROLL, delayMillis)
    }

    fun scrollNext() {
        (layoutManager as? LinearLayoutManager)?.let { layoutManager ->
            layoutManager.startSmoothScroll(
                createScroller(layoutManager.findLastCompletelyVisibleItemPosition() + 1)
            )
        }
        scrollHandler.sendEmptyMessageDelayed(WHAT_SCROLL, delayMillis)
    }

    private class ScrollHandler(autoScrollableRecyclerView: ContentPagerRecyclerView) : Handler() {

        private val autoScrollViewPager =
            WeakReference<ContentPagerRecyclerView>(autoScrollableRecyclerView)

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            autoScrollViewPager.get()?.scrollNext()
        }
    }

    companion object {
        const val WHAT_SCROLL = 1
    }
}