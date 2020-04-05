package revolhope.splanes.com.aikver.presentation.common.widget.imagepager
/*

import android.content.Context
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.viewpager2.widget.ViewPager2
import revolhope.splanes.com.aikver.R
import kotlinx.android.synthetic.main.component_image_pager.view.*

class ImagePager @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle), View.OnClickListener {

    private lateinit var links: List<String>

    init {
        View.inflate(context, R.layout.component_image_pager, this)
    }

    fun initialize(links: List<String>) {

        this.links = links

        crumbView.configureSteps(links.size)

        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager2.adapter = ImagePagerAdapter(
            context,
            links.toMutableList(),
            fun(position: Int) = crumbView.removeStep(position)
        )
        viewPager2.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                crumbView.setSelected(position)
            }
        })

        closeButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        TransitionManager.beginDelayedTransition(parent as ViewGroup, ChangeBounds().apply {
            duration = 300
        })
        (parent as ViewGroup).removeView(this)
    }

    fun getSelectedLink(): String = links[viewPager2.currentItem]
}*/
