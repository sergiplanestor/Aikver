package revolhope.splanes.com.aikver.presentation.common.widget.imagepager
/*

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import revolhope.splanes.com.aikver.R

class CrumbView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    private var totalSteps = 0
    private var currentSelected = 0
    private var steps = listOf<ImageView>().toMutableList()

    init {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER
    }

    fun configureSteps(totalSteps: Int, currentSelected: Int = 0) {
        this.totalSteps = totalSteps
        this.currentSelected = currentSelected
        reset(currentSelected)
    }

    fun setSelected(selected: Int) {
        move(selected)
    }

    fun previous() {
        move(currentSelected-1)
    }

    fun next() {
        move(currentSelected+1)
    }

    fun removeStep(position: Int) {
        totalSteps--
        steps.removeAt(position)
        reset(currentSelected)
    }

    private fun move(currentPosition: Int) {

        currentSelected = when {
            currentPosition > totalSteps - 1 -> {
                totalSteps - 1
            }
            currentPosition < 0 -> {
                0
            }
            else -> {
                currentPosition
            }
        }

        for (index in 0 until totalSteps) {
            steps[index].setImageDrawable(context.getDrawable(
                if (index == currentSelected) {
                    R.drawable.component_crumb_selected
                } else {
                    R.drawable.component_crumb_unselected
                }
            ))
        }
    }

    private fun reset(currentPosition: Int) {

        removeAllViews()
        steps.clear()

        for (index in 0 until totalSteps) {

            val image = ImageView(context).apply {

                layoutParams = LayoutParams(30, 30).apply {
                    marginStart = 10
                    marginEnd = 10
                }
            }

            steps.add(image)
            addView(image)
        }

        move(currentPosition)
    }

}*/
