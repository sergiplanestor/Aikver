package revolhope.splanes.com.aikver.presentation.common.widget

import revolhope.splanes.com.aikver.R

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.component_score_view.view.*
import revolhope.splanes.com.aikver.presentation.common.dpToPx

/**
 * @author sergiplanes on 2020-02-21
 */

class ScoreView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    private var stars = listOf<ImageView>().toMutableList()

    init {
        View.inflate(context, R.layout.component_score_view, this)
        initialize()
    }

    private fun initialize() {

        stars.addAll(listOf(
            firstStar,
            secondStar,
            thirdStar,
            forthStar,
            fifthStar
        ))

        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                updateScore(progress)
                updateScoreText(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

    }

    fun setStarSize(dpSize: Int) =
        stars.forEach {
            it.layoutParams = LayoutParams(dpToPx(context, dpSize), dpToPx(context, dpSize))
        }

    fun setDark() =
        stars.forEach {
            it.imageTintList =
                ColorStateList.valueOf(resources.getColor(R.color.colorScoreDark, null))
        }

    fun hideNumeric() {
        scoreTextView.visibility = View.GONE
    }

    fun isCentered(isCentered: Boolean) {
        if (isCentered) {
            starLayout.layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
        }
    }

    fun isEditable(isEditable: Boolean) {
        seekBar.isEnabled = isEditable
    }

    fun updateScore(score: Int) {

        if (score == 0) {
            clearStars()
            return
        }

        val halfScore = score / 2

        for (index in 0 until stars.size) {

            if (score % 2 == 0) {

                stars[index].setImageResource(
                    if (index < halfScore) {
                        R.drawable.round_star_24
                    } else {
                        R.drawable.round_star_border_24
                    }
                )

            } else {

                stars[index].setImageResource(
                    when {
                        index < halfScore -> {
                            R.drawable.round_star_24
                        }
                        index == halfScore -> {
                            R.drawable.round_star_half_24
                        }
                        else -> {
                            R.drawable.round_star_border_24
                        }
                    }
                )

            }
        }
    }

    fun updateScoreText(score: Int) {
        scoreTextView.text = context.getString(R.string.score_view_placeholder, score)
    }

    fun clearStars() {

        for (index in 0 until stars.size) {
            stars[index].setImageResource(R.drawable.round_star_border_24)
        }

    }

    fun getScore() : Int = seekBar.progress
}