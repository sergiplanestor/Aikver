package revolhope.splanes.com.aikver.data.serie

import revolhope.splanes.com.aikver.domain.Serie

interface SerieDataSource {

    suspend fun insert(
        serie: Serie,
        onSuccess: (success: Boolean) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    )

    suspend fun fetchAll(
        onSuccess: (series: List<Serie>) -> Unit,
        onFailure: (throwable: Throwable) -> Unit,
        forceCall: Boolean = false
    )

    suspend fun fetchRecent(
        onSuccess: (series: List<Serie>) -> Unit,
        onFailure: (throwable: Throwable) -> Unit,
        forceCall: Boolean = false
    )

    suspend fun edit(
        serie: Serie,
        onSuccess: (success: Boolean) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    )

    suspend fun delete(
        serie: Serie,
        onSuccess: (success: Boolean) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    )
}