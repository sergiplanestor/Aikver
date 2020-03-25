package bemobile.splanes.com.aikver.framework.helper

import android.os.Build
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*

object CryptoHelper {

    fun sha256(value: String) : String {

        val md = MessageDigest.getInstance("SHA-256")
        md.update(value.toByteArray())
        val rawRes = md.digest()
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Base64.getEncoder().encodeToString(rawRes)
        } else {
            String.format("%064x", BigInteger(1, rawRes))
        }
    }

}