package revolhope.splanes.com.core.domain.mapper

import revolhope.splanes.com.core.data.entity.api.configuration.ConfigurationEntity
import revolhope.splanes.com.core.data.entity.api.configuration.ImageConfigurationEntity
import revolhope.splanes.com.core.domain.model.config.Configuration
import revolhope.splanes.com.core.domain.model.config.ImageConfiguration

object ConfigurationMapper {

    fun fromEntityToModel(entity: ConfigurationEntity): Configuration =
        Configuration(
            imageConfigurationEntity = entity.imageConfigEntity.let(::fromImageEntityToModel),
            changeKeysValue = entity.changeKeysValue ?: listOf()
        )

    private fun fromImageEntityToModel(entity: ImageConfigurationEntity?): ImageConfiguration =
        if (entity == null) {
            ImageConfiguration()
        }
        else {
            ImageConfiguration(
                baseUrl = entity.baseUrl ?: "",
                secureBaseUrl = entity.secureBaseUrl ?: "",
                backdropSizes = entity.backdropSizes ?: listOf(),
                logoSizes = entity.logoSizes ?: listOf(),
                posterSizes = entity.posterSizes ?: listOf(),
                profileSizes = entity.profileSizes ?: listOf(),
                stillSizes = entity.stillSizes ?: listOf()
            )
        }
}