package bemobile.splanes.com.aikver.domain

import java.util.*

data class Serie(
    val id: String = UUID.randomUUID().toString().replace("-", ""),
    val title: String,
    val imageUrl: String? = null,
    val platform: Platform?,
    val category: String?,
    var score: Int,
    val userCreator: String?,
    var numScorers: Int = 1,
    val dateCreation: Long = System.currentTimeMillis(),
    var userScorers: List<String?>?
) {

    fun addScore(score: Int, user: String?) {
        this.score = (score + (this.score * numScorers)) / (numScorers + 1)
        numScorers++
        userScorers = userScorers?.toMutableList().apply { this?.add(user) }
    }

    fun applyChanges(newSerie: Serie) {
        this.userScorers = newSerie.userScorers
        this.score = newSerie.score
    }
}