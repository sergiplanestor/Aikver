package revolhope.splanes.com.aikver.domain

import revolhope.splanes.com.aikver.framework.helper.CryptoHelper

data class User(
    val username: String,
    val pwd: String
) {
    constructor(username: String) : this(
        username,
        CryptoHelper.sha256(username)
    )
}