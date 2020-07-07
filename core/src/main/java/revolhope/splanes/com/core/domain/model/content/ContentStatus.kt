package revolhope.splanes.com.core.domain.model.content

import java.util.Locale

enum class ContentStatus(val value: String) {
    RUMORED("RUMORED"),
    PLANNED("PLANNED"),
    IN_PRODUCTION("IN PRODUCTION"),
    POST_PRODUCTION("POST PRODUCTION"),
    RELEASED("RELEASED"),
    CANCELED("CANCELED"),
    ENDED("ENDED"),
    IN_PROGRESS("RETURNING SERIES"),
    UNKNOWN("UNKNOWN");

    companion object {
        fun parse(value: String?): ContentStatus =
            values().find { it.value == value?.toUpperCase(Locale.ROOT) } ?: UNKNOWN
    }
}