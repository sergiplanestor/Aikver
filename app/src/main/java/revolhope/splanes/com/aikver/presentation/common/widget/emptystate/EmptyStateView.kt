package revolhope.splanes.com.aikver.presentation.common.widget.emptystate

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.component_empty_state_view.view.emptyAction
import kotlinx.android.synthetic.main.component_empty_state_view.view.emptyImage
import kotlinx.android.synthetic.main.component_empty_state_view.view.emptySubtitle
import kotlinx.android.synthetic.main.component_empty_state_view.view.emptyTitle
import kotlinx.android.synthetic.main.component_empty_state_view.view.middleSpace
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.presentation.common.dpToPx
import revolhope.splanes.com.aikver.presentation.common.visible

class EmptyStateView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    attrDefInt: Int = 0
) : ConstraintLayout(context, attributeSet, attrDefInt) {

    enum class Mode(val value: Int) {
        LIGHT(0),
        DARK(1);

        companion object {
            fun from(value: Int) : Mode = values().find { it.value == value } ?: DARK
        }
    }

    private var action: (() -> Unit)? = null
    private val attributeMap = mapOf(
        R.styleable.EmptyStateView[R.styleable.EmptyStateView_title] to
                fun(ta: TypedArray, i: Int) { ta.getString(i)?.run { setTitle(this) } },

        R.styleable.EmptyStateView[R.styleable.EmptyStateView_subtitle] to
                fun(ta: TypedArray, i: Int) { ta.getString(i)?.run { setSubtitle(this) } },

        R.styleable.EmptyStateView[R.styleable.EmptyStateView_actionText] to
                fun(ta: TypedArray, i: Int) { ta.getString(i)?.run { setActionText(this) } },

        R.styleable.EmptyStateView[R.styleable.EmptyStateView_imageSize] to
                fun(ta: TypedArray, i: Int) { ta.getInt(i, 0).run { setImageSize(this) } },

        R.styleable.EmptyStateView[R.styleable.EmptyStateView_mode] to
                fun(ta: TypedArray, i: Int) { ta.getInt(i, 0).run {
                    setMode(Mode.from(this)) }
                }
    ).toSortedMap()

    init {
        View.inflate(context, R.layout.component_empty_state_view, this)
        attributeSet?.let { setupAttributes(context, attributeSet) }
        bindViews()
    }

    private fun setupAttributes(context: Context, attrs: AttributeSet) {
        val set = attributeMap.keys.toIntArray().apply { sort() }
        val ta = context.obtainStyledAttributes(attrs, set)
        (0 until ta.indexCount).map(ta::getIndex).forEach { attributeMap[set[it]]?.invoke(ta, it) }
        ta.recycle()
    }

    private fun bindViews() {
        emptyAction.setOnClickListener { action?.invoke() }
    }

    fun setTitle(title: String) {
        emptyTitle.text = title
        emptyTitle.visible()
        middleSpace.visible()
    }

    fun setSubtitle(subtitle: String) {
        emptySubtitle.text = subtitle
        emptySubtitle.visible()
    }

    fun setActionText(actionText: String) {
        emptyAction.text = actionText
        emptyAction.visible()
    }

    // TODO: Change that shit.. resize automatically!
    fun setImageSize(dp: Int) {
        emptyImage.layoutParams = emptyImage.layoutParams.apply {
            height = dpToPx(context, dp)
        }
    }

    fun setMode(mode: Mode) {
        when (mode) {
            Mode.LIGHT -> {
                emptyTitle.setTextColor(context.getColor(R.color.whiteAlpha90))
                emptySubtitle.setTextColor(context.getColor(R.color.whiteAlpha90))
                (emptyImage.drawable as LayerDrawable).run {
                    (findDrawableByLayerId(R.id.emptyBackground) as GradientDrawable).color =
                        ColorStateList.valueOf(context.getColor(R.color.whiteAlpha20))
                }
            }
            Mode.DARK -> {
                emptyTitle.setTextColor(context.getColor(android.R.color.black))
                emptySubtitle.setTextColor(context.getColor(android.R.color.black))
                (emptyImage.drawable as LayerDrawable).run {
                    (findDrawableByLayerId(R.id.emptyBackground) as ShapeDrawable).paint.color =
                        context.getColor(R.color.blackAlpha20)
                }
            }
        }
    }

    fun setAction(action: () -> Unit) { this.action = action }

}