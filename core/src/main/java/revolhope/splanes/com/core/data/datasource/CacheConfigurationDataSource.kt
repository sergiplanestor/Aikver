package revolhope.splanes.com.core.data.datasource

import revolhope.splanes.com.core.domain.model.config.Configuration

object CacheConfigurationDataSource {

    private var config: Configuration? = null

    fun insertConfig(config: Configuration?) { this.config = config }

    fun fetchConfig() = config
}