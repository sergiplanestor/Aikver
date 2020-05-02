package revolhope.splanes.com.aikver.presentation.common.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.component_content_selector.view.film
import revolhope.splanes.com.aikver.R

class ContentSelector @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    enum class Tab(val value: Int) {
        FILM(0),
        SERIE(1)
    }

    var selection: Tab = Tab.SERIE

    init {
        View.inflate(context, R.layout.component_content_selector, this)
        film.setOnCheckedChangeListener { _, checked ->
            selection = if (checked) Tab.FILM else Tab.SERIE
        }
    }
}