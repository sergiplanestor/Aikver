package revolhope.splanes.com.core.domain.net

sealed class Response<out T> {
    // TODO: Loading state? see: https://stackoverflow.com/questions/44243763/how-to-make-sealed-classes-generic-in-kotlin
    data class Success<T>(val data: T) : Response<T>()
    data class Error(val error: String = "", val exception: Throwable?) : Response<Nothing>()
}