package revolhope.splanes.com.aikver.presentation.feature.menu.common.content.fragment.master.adapter

data class ContentDetailsUiModel(
    val title: String,
    val content: String,
    val isOverview: Boolean = false,
    val isLink: Boolean = false
)