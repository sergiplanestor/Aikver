package revolhope.splanes.com.core.domain.helper

import java.math.BigInteger
import java.security.MessageDigest

object CryptoHelper {
    fun sha256(value: String) : String {
        val md = MessageDigest.getInstance("SHA-256")
        md.update(value.toByteArray())
        val rawRes = md.digest()
        return String.format("%064x", BigInteger(1, rawRes))
    }
}