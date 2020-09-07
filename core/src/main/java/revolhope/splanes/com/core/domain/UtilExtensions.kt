package revolhope.splanes.com.core.domain

import revolhope.splanes.com.core.domain.model.content.ContentDetails
import revolhope.splanes.com.core.domain.model.content.CustomContent
import java.math.BigInteger
import java.security.MessageDigest

fun sha256(value: String) : String {
    val md = MessageDigest.getInstance("SHA-256")
    md.update(value.toByteArray())
    val rawRes = md.digest()
    return String.format("%064x", BigInteger(1, rawRes))
}

fun <T : CustomContent<ContentDetails>> MutableList<T>.filterRepeated(): MutableList<T> {
    val iterator = this.iterator()
    while (iterator.hasNext()) {
        iterator.next().let { content1 ->
            if (any { content1.content.id == it.content.id && content1 != it }) {
                iterator.remove()
            }
        }
    }
    return this
}