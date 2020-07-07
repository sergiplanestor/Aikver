package revolhope.splanes.com.aikver.presentation.common.widget.switcher

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.component_switcher_view.view.leftOptionTextView
import kotlinx.android.synthetic.main.component_switcher_view.view.rightOptionTextView
import revolhope.splanes.com.aikver.R

class SwitcherView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    attrDefStyle: Int = 0
) : ConstraintLayout(context, attributeSet, attrDefStyle) {

    enum class Option(val value: Int) {
        LEFT(1),
        RIGHT(0);
        companion object {
            fun from(value: Int): Option? = values().find { it.value == value }
        }
    }

    private lateinit var optionSelected: Option
    private var changeListener: ((Option) -> Unit)? = null

    private val attributeMap = mapOf(
        R.styleable.SwitcherView[R.styleable.SwitcherView_leftOption] to
                fun(ta: TypedArray, i: Int) { ta.getString(i)?.run { setLeftOption(this) } },

        R.styleable.SwitcherView[R.styleable.SwitcherView_rightOption] to
                fun(ta: TypedArray, i: Int) { ta.getString(i)?.run { setRightOption(this) } },

        R.styleable.SwitcherView[R.styleable.SwitcherView_defaultOptionSelected] to
                fun(ta: TypedArray, i: Int) {
                    Option.from(ta.getInt(i, 0))?.run { setOptionSelected(this) }
                }
    ).toSortedMap()

    init {
        View.inflate(context, R.layout.component_switcher_view, this)
        attributeSet?.let { setupAttributes(context, attributeSet) }
        bind()
    }

    private fun setupAttributes(context: Context, attrs: AttributeSet) {
        val set = attributeMap.keys.toIntArray().apply { sort() }
        val ta = context.obtainStyledAttributes(attrs, set)
        (0 until ta.indexCount).map(ta::getIndex).forEach { attributeMap[set[it]]?.invoke(ta, it) }
        ta.recycle()
    }

    private fun bind() {
        leftOptionTextView.setOnClickListener(::onTextClick)
        rightOptionTextView.setOnClickListener(::onTextClick)
    }

    private fun setLeftOption(leftOptionText: String) {
        leftOptionTextView.text = leftOptionText
    }

    private fun setRightOption(rightOptionText: String) {
        rightOptionTextView.text = rightOptionText
    }

    private fun onTextClick(v: View) = setOptionSelected(
        if (v.id == R.id.leftOptionTextView) Option.LEFT else Option.RIGHT
    )

    private fun setOptionSelected(option: Option) {
        optionSelected = option
        when (option) {
            Option.LEFT -> {
                leftOptionTextView.background = context.getDrawable(R.drawable.badge_filled_white)
                leftOptionTextView.setTextColor(context.getColor(android.R.color.black))
                rightOptionTextView.background = context.getDrawable(R.drawable.badge_outlined_white)
                rightOptionTextView.setTextColor(context.getColor(R.color.whiteAlpha90))
            }
            Option.RIGHT -> {
                leftOptionTextView.background = context.getDrawable(R.drawable.badge_outlined_white)
                leftOptionTextView.setTextColor(context.getColor(R.color.whiteAlpha90))
                rightOptionTextView.background = context.getDrawable(R.drawable.badge_filled_white)
                rightOptionTextView.setTextColor(context.getColor(android.R.color.black))
            }
        }
        TransitionManager.beginDelayedTransition(this)
        changeListener?.invoke(optionSelected)
    }

    fun getOptionSelected(): Option = optionSelected

    fun setOnCheckedChangeListener(listener: (Option) -> Unit) {
        changeListener = listener
    }
}