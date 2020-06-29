package revolhope.splanes.com.aikver.presentation.feature.menu.common.content

import revolhope.splanes.com.core.domain.model.content.Network
import revolhope.splanes.com.core.domain.model.content.serie.CustomSerie
import revolhope.splanes.com.core.domain.model.content.serie.Serie

data class SerieCustomInfoUiModel(
    val serie: Serie,
    val haveSeen: Boolean = false,
    val score: Int = -1,
    val network: Network = Network.UNKNOWN,
    val comments: String = ""
)