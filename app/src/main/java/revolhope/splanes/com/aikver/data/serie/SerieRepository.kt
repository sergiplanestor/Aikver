package revolhope.splanes.com.aikver.data.serie

import revolhope.splanes.com.aikver.domain.Serie

class SerieRepository(private val serieDataSource: SerieDataSource) {

    suspend fun insert(
        serie: Serie,
        onSuccess: (success: Boolean) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    ) = serieDataSource.insert(serie, onSuccess, onFailure)

    suspend fun fetchAll(
        onSuccess: (series: List<Serie>) -> Unit,
        onFailure: (throwable: Throwable) -> Unit,
        forceCall: Boolean = false
    ) = serieDataSource.fetchAll(onSuccess, onFailure, forceCall)

    suspend fun fetchRecent(
        onSuccess: (series: List<Serie>) -> Unit,
        onFailure: (throwable: Throwable) -> Unit,
        forceCall: Boolean = false
    ) = serieDataSource.fetchRecent(onSuccess, onFailure, forceCall)

    suspend fun edit(
        serie: Serie,
        onSuccess: (success: Boolean) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    ) = serieDataSource.edit(serie, onSuccess, onFailure)

    suspend fun delete(
        serie: Serie,
        onSuccess: (success: Boolean) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    ) = serieDataSource.delete(serie, onSuccess, onFailure)
}