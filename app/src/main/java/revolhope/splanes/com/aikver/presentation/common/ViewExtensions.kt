package revolhope.splanes.com.aikver.presentation.common

import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.presentation.common.widget.popup.PopupAlert
import revolhope.splanes.com.aikver.presentation.common.widget.popup.PopupModel
import revolhope.splanes.com.core.domain.model.UserAvatar

fun dpToPx(context: Context, dp: Int): Int =
    dp * (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)

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

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible(isGone: Boolean = true) {
    this.visibility = if (isGone) View.GONE else View.INVISIBLE
}

fun View.visibility(show: Boolean, isGone: Boolean = true) =
    if (show) this.visible() else this.invisible(isGone)

fun ImageView.loadCircular(url: String) {
    Glide.with(context).load(url).circleCrop().into(this)
}

fun ImageView.loadGroupIcon(name: String, color: String) {
    Glide.with(context).load(
        "https://eu.ui-avatars.com/api/?" +
                "name=${name.replace(" ", "+")}&" +
                "background=455A64&" +
                "color=${color}&" +
                "format=png"
    ).circleCrop().into(this)
}

fun ImageView.loadAvatar(avatar: UserAvatar) {
    val baseUrl = "https://api.adorable.io/avatars/face"
    Glide.with(context)
        .load("$baseUrl/${avatar.eyes}/${avatar.nose}/${avatar.mouth}/${avatar.color}")
        .circleCrop()
        .into(this)
}