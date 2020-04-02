package revolhope.splanes.com.aikver.presentation.common.widget.serieviewer

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import revolhope.splanes.com.aikver.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.component_serie_view.view.*

class SerieView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
): ConstraintLayout(context, attrs, defStyle) {

    init {
        View.inflate(context, R.layout.component_serie_view, this)
    }

    fun initialize(url: String?, title: String, score: Int) {
        if (url != null) Glide.with(context).load(url).into(imageView)
        else imageView.setImageResource(R.drawable.image_placeholder)
        titleTextView.text = title
        with(scoreView) {
            updateScore(score)
            isCentered(true)
            isEditable(false)
            hideNumeric()
            setStarSize(20)
        }
    }
}