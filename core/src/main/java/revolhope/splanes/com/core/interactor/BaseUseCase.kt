package revolhope.splanes.com.core.interactor

import revolhope.splanes.com.core.domain.ResponseState
import java.lang.Exception

abstract class BaseUseCase<REQ, RES> {

    suspend operator fun invoke(req: REQ): ResponseState<RES> =
        try {
            execute(req)?.let {
                ResponseState.Success(data = it)
            } ?: ResponseState.Error(Exception())
        } catch (e: Exception) {
            ResponseState.Error(e)
        }

    abstract suspend fun execute(req: REQ): RES?
}