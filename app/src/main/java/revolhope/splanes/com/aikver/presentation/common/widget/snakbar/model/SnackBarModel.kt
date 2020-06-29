package revolhope.splanes.com.aikver.presentation.common.widget.snakbar.model

sealed class SnackBarModel(open val onClick: (() -> Unit)?) {

    data class Success(
        val message: String,
        override val onClick: (() -> Unit)? = null
    ) : SnackBarModel(onClick)

    data class Error(
        val message: String? = null,
        override val  onClick: (() -> Unit)? = null
    ) : SnackBarModel(onClick)
}