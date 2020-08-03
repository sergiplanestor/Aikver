package revolhope.splanes.com.aikver.presentation.common.widget.emptystate

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.component_empty_state_view.view.emptyAction
import kotlinx.android.synthetic.main.component_empty_state_view.view.emptyImage
import kotlinx.android.synthetic.main.component_empty_state_view.view.emptySubtitle
import kotlinx.android.synthetic.main.component_empty_state_view.view.emptyTitle
import kotlinx.android.synthetic.main.component_empty_state_view.view.rootLayout
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

    enum class Orientation(val value: Int) {
        HORIZONTAL(0),
        VERTICAL(1);

        companion object {
            fun from(value: Int) : Orientation = values().find { it.value == value } ?: VERTICAL
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
                },
        R.styleable.EmptyStateView[R.styleable.EmptyStateView_orientation] to
                fun(ta: TypedArray, i: Int) {
                    ta.getInt(i, 0).run {
                        setOrientation(Orientation.from(this))
                    }
                }
    ).toSortedMap()

    init {
        View.inflate(context, R.layout.component_empty_state_view, this)
        attributeSet?.let { setupAttributes(context, attributeSet) }
        bindViews(attributeSet == null)
        layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )
    }

    private fun setupAttributes(context: Context, attrs: AttributeSet) {
        val set = attributeMap.keys.toIntArray().apply { sort() }
        val ta = context.obtainStyledAttributes(attrs, set)
        (0 until ta.indexCount).map(ta::getIndex).forEach { attributeMap[set[it]]?.invoke(ta, it) }
        ta.recycle()
    }

    private fun bindViews(areAttrNull: Boolean) {
        emptyAction.setOnClickListener { action?.invoke() }
        if (areAttrNull) {
            setMode(Mode.DARK)
            setOrientation(Orientation.VERTICAL)
        }
    }

    fun setTitle(title: String) {
        emptyTitle.text = title
        emptyTitle.visible()
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
            dpToPx(context, dp).let {
                height = it
                width = it
            }
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
                    (findDrawableByLayerId(R.id.emptyBackground) as GradientDrawable).color =
                        ColorStateList.valueOf(context.getColor(R.color.blackAlpha20))
                }
            }
        }
    }

    fun setOrientation(orientation: Orientation) {
        when (orientation) {
            Orientation.HORIZONTAL -> {
                rootLayout.orientation = LinearLayout.HORIZONTAL
                emptyImage.layoutParams = emptyImage.layoutParams.apply {
                    (this as? LinearLayout.LayoutParams)?.gravity = Gravity.START
                }
                emptyTitle.gravity = Gravity.START
                emptySubtitle.gravity = Gravity.START
                emptyAction.layoutParams = emptyAction.layoutParams.apply {
                    (this as? LinearLayout.LayoutParams)?.gravity = Gravity.START
                }
            }
            Orientation.VERTICAL -> {
                rootLayout.orientation = LinearLayout.VERTICAL
                emptyImage.layoutParams = emptyImage.layoutParams.apply {
                    (this as? LinearLayout.LayoutParams)?.gravity = Gravity.CENTER
                }
                emptyTitle.gravity = Gravity.CENTER
                emptySubtitle.gravity = Gravity.CENTER
                emptyAction.layoutParams = emptyAction.layoutParams.apply {
                    (this as? LinearLayout.LayoutParams)?.gravity = Gravity.CENTER
                }
            }
        }
    }

    fun setAction(action: () -> Unit) { this.action = action }

}