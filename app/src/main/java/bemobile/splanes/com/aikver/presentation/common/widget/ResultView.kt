package bemobile.splanes.com.aikver.presentation.common.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import bemobile.splanes.com.aikver.R
import kotlinx.android.synthetic.main.component_result.view.*

class ResultView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
): ConstraintLayout(context, attributeSet, defStyle) {

    init {
        View.inflate(context, R.layout.component_result, this)
    }

    fun initialize(result: Boolean) {

        if (result) {
            resultAnimation.setAnimation(R.raw.anim_success)
            resultTitle.text = context.getString(R.string.result_ok_title)
            resultDescription.text = context.getString(R.string.result_ok_description)
        } else {
            resultAnimation.setAnimation(R.raw.anim_success)
            resultTitle.text = context.getString(R.string.result_ko_title)
            resultDescription.text = context.getString(R.string.result_ko_description)
        }
    }

    fun play() = resultAnimation.playAnimation()

}