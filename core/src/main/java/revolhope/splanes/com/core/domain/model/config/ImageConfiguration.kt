package revolhope.splanes.com.core.domain.model.config

data class ImageConfiguration(
    val baseUrl: String,
    val secureBaseUrl: String,
    val backdropSizes: List<String>,
    val logoSizes: List<String>,
    val posterSizes: List<String>,
    val profileSizes: List<String>,
    val stillSizes: List<String>
) {
    constructor(): this(
        baseUrl = "",
        secureBaseUrl = "",
        backdropSizes = listOf(),
        logoSizes = listOf(),
        posterSizes = listOf(),
        profileSizes = listOf(),
        stillSizes = listOf()
    )
}