package revolhope.splanes.com.aikver.framework.service

import revolhope.splanes.com.aikver.data.serie.SerieDataSource
import revolhope.splanes.com.aikver.domain.Serie
import revolhope.splanes.com.aikver.framework.helper.FirebaseHelper

class SerieService(private val firebaseHelper: FirebaseHelper) : SerieDataSource {

    companion object {
        var seriesCached: List<Serie>? = null
        var recentSeriesCached: List<Serie>? = null
    }

    override suspend fun insert(
        serie: Serie,
        onSuccess: (success: Boolean) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    ) = firebaseHelper.insertSerie(serie, onSuccess, onFailure)

    override suspend fun fetchAll(
        onSuccess: (series: List<Serie>) -> Unit,
        onFailure: (throwable: Throwable) -> Unit,
        forceCall: Boolean
    ) = if (forceCall || seriesCached.isNullOrEmpty()) {
        firebaseHelper.fetchAll(
            fun (series: List<Serie>) {
                with(series) {
                    seriesCached = this
                    recentSeriesCached = getRecent(this)
                    onSuccess(this)
                }
            },
            onFailure)
    } else if (seriesCached != null) onSuccess(seriesCached!!)
    else onFailure(Exception("There's no series cached, use forceCall = true"))

    override suspend fun fetchRecent(
        onSuccess: (series: List<Serie>) -> Unit,
        onFailure: (throwable: Throwable) -> Unit,
        forceCall: Boolean
    ) = if (forceCall || seriesCached.isNullOrEmpty()) {
        firebaseHelper.fetchAll(
            fun (series: List<Serie>) {
                with(series) {
                    seriesCached = this
                    recentSeriesCached = getRecent(this)
                    onSuccess(this)
                }
            },
            onFailure)
    } else if (recentSeriesCached != null) onSuccess(recentSeriesCached!!)
    else onFailure(Exception("There's no recent series cached, use forceCall = true"))

    private fun getRecent(series: List<Serie>): List<Serie> {

        val ordered = series.sortedWith(
            Comparator { first, second ->
                when {
                    first.dateCreation > second.dateCreation -> -1
                    first.dateCreation == second.dateCreation -> 0
                    else /* first.dateCreation < second.dateCreation */ -> 1
                }
            }
        )

        return if (series.count() <= 10) ordered
        else ordered.subList(0, 10)
    }

    override suspend fun edit(
        serie: Serie,
        onSuccess: (success: Boolean) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    ) {
        firebaseHelper.updateSerie(
            serie,
            {
                if (it) {
                    updateSerie(serie)
                }
                onSuccess(it)
            },
            onFailure
        )
    }

    override suspend fun delete(
        serie: Serie,
        onSuccess: (success: Boolean) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    ) {
        firebaseHelper.deleteSerie(
            serie,
            {
                if (it) {
                    deleteSerie(serie)
                }
                onSuccess(it)
            },
            onFailure
        )
    }

    private fun updateSerie(serie: Serie) =
        with(serie) {
            seriesCached?.find { it.id == this.id }.also { it?.applyChanges(this) }
            recentSeriesCached?.find { it.id == this.id }.also { it?.applyChanges(this) }
        }

    private fun deleteSerie(serie: Serie) =
        with(serie) {
            seriesCached?.find { it.id == this.id }.run {
                seriesCached?.toMutableList()?.remove(this)
            }
            recentSeriesCached?.find { it.id == this.id }.run {
                recentSeriesCached?.toMutableList()?.remove(this)
            }
        }
}