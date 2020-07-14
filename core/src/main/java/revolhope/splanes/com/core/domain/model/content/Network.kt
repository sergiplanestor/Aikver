package revolhope.splanes.com.core.domain.model.content

import java.util.*

enum class Network(val id: Int) {
    UNKNOWN(-1),
    HBO(0),
    NETFLIX(1),
    AMAZON_PRIME(2),
    MOVISTAR(3);

    companion object {
        fun fromValue(id: Int): Network = values().find { it.id == id } ?: UNKNOWN

        // TODO: This should not be hardcoded values.. Here can't access to resource so...
        //  I might not have way to get it from strings.xml, search solution
        fun fromName(name: String): Network = when (name.toLowerCase(Locale.ROOT)) {
            "hbo" -> HBO
            "netflix" -> NETFLIX
            "amazon prime" -> AMAZON_PRIME
            "movistar" -> MOVISTAR
            else -> UNKNOWN
        }
    }

    override fun toString(): String = when (this) {
        HBO -> "HBO"
        NETFLIX -> "Netflix"
        AMAZON_PRIME -> "Amazon Prime"
        MOVISTAR -> "Movistar"
        UNKNOWN -> "Desconocido"
    }
}