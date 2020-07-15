package revolhope.splanes.com.aikver.presentation.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.LinearGradient
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Build
import android.text.Layout
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.fragment_splash.descriptionTextView
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.presentation.common.widget.popup.PopupAlert
import revolhope.splanes.com.aikver.presentation.common.widget.popup.PopupModel
import revolhope.splanes.com.core.domain.model.user.UserAvatar

/* --- Util functions --- */

fun dpToPx(context: Context, dp: Int): Int =
    dp * (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)

fun dpToPx(context: Context, dp: Float): Float =
    dp * (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)

/* --- Popup --- */

fun popup(fm: FragmentManager, model: PopupModel) = PopupAlert(model).show(fm)

fun popupError(context: Context, fm: FragmentManager, action: (() -> Unit)? = null) {
    popup(
        fm = fm,
        model = PopupModel(
            title = context.getString(R.string.error_title),
            message = context.getString(R.string.error_message_default),
            buttonPositive = context.getString(R.string.ok),
            buttonPositiveListener = action
        )
    )
}

/* --- View visibility --- */

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible(isGone: Boolean = true) {
    this.visibility = if (isGone) View.GONE else View.INVISIBLE
}

fun View.visibility(show: Boolean, isGone: Boolean = true) =
    if (show) this.visible() else this.invisible(isGone)

/* --- Image loading --- */

fun ImageView.loadUrl(url: String) {
    Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(this)
}

fun ImageView.loadCircular(url: String) {
    Glide.with(context).load(url).circleCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(this)
}

fun ImageView.loadGroupIcon(name: String, color: String) {
    loadCircular(
        "https://eu.ui-avatars.com/api/?" +
                "name=${name.replace(" ", "+")}&" +
                "background=455A64&" +
                "color=${color}&" +
                "format=png"
    )
}

fun ImageView.loadAvatar(avatar: UserAvatar) {
    loadCircular(
        "https://api.adorable.io/avatars/face" +
                "/${avatar.eyes}" +
                "/${avatar.nose}" +
                "/${avatar.mouth}" +
                "/${avatar.color}"
    )
}

/* --- TextView --- */

fun TextView.justify(isJustified: Boolean = true) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        justificationMode = if (isJustified) {
            Layout.JUSTIFICATION_MODE_INTER_WORD
        } else {
            Layout.JUSTIFICATION_MODE_NONE
        }
    }
}

fun TextView.setAsLink() {
    setTextColor(context.getColor(R.color.colorAccent))
    text = SpannableString(text.toString()).apply {
        setSpan(UnderlineSpan(), 0, text.toString().length, 0)
    }
    setOnClickListener {
        (context as? Activity)?.startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse(text.toString()))
        )
    }
}

/* ---  --- */
fun Int.toCustomString(): String {
    val s = toString()
    if (s.length / 3 != 0) {
        return s.reversed().chunked(3).let { chunks ->
            var aux = ""
            chunks.forEach { aux += "$it." }
            aux.removeSuffix(".").reversed()
        }
    }
    return s
}

/* --- SnackBar Parent --- */

fun View?.findSuitableParent(): ViewGroup? {
    var view = this
    var fallback: ViewGroup? = null
    do {
        when (view) {
            is CoordinatorLayout -> return view
            is FrameLayout -> {
                if (view.id == android.R.id.content) return view else fallback = view
            }
        }

        view = if (view?.parent is View) view.parent as View else null

    } while (view != null)

    return fallback
}
