package revolhope.splanes.com.aikver.presentation.common.widget.popup

data class PopupModel(
    val title: String? = null,
    val message: String,
    val buttonPositive: String,
    val buttonNegative: String? = null,
    val buttonPositiveListener: (() -> Unit)? = null,
    val buttonNegativeListener: (() -> Unit)? = null,
    val isCancelable: Boolean = false
)