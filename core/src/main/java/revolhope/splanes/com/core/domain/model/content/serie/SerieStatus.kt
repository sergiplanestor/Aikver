package revolhope.splanes.com.core.domain.model.content.serie

import java.util.*

enum class SerieStatus(val value: String) {
    ENDED("ENDED"),
    IN_PROGRESS("RETURNING SERIES"),
    UNKNOWN("UNKNOWN");

    companion object {
        fun parse(value: String?): SerieStatus =
            values().find { it.value == value?.toUpperCase(Locale.ROOT) } ?: UNKNOWN
    }
}