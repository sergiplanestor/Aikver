package bemobile.splanes.com.aikver.presentation.common.widget.popup

import android.view.View

data class PopupModel(
    val title: String? = null,
    val message: String,
    val buttonPositive: String,
    val buttonNegative: String? = null,
    val buttonPositiveListener: View.OnClickListener,
    val buttonNegativeListener: View.OnClickListener? = null,
    val isCancelable: Boolean = false
)