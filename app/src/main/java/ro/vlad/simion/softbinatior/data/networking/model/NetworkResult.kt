package ro.vlad.simion.softbinatior.data.networking.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

sealed class NetworkResult<out T> {

    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Failure(val msg: Throwable?) : NetworkResult<Nothing>()
    object Loading : NetworkResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success $data"
            is Failure -> "Failure $msg"
            Loading -> "Loading"
        }
    }
}

fun <T, R> NetworkResult<T>.map(transform: (T) -> R): NetworkResult<R> {
    return when (this) {
        is NetworkResult.Success -> NetworkResult.Success(transform(data))
        is NetworkResult.Failure -> NetworkResult.Failure(msg)
        NetworkResult.Loading -> NetworkResult.Loading
    }
}

fun <T> Flow<NetworkResult<T>>.doOnSuccess(action: suspend (T) -> Unit): Flow<NetworkResult<T>> =
    transform { result ->
        if (result is NetworkResult.Success) {
            action(result.data)
        }
        return@transform emit(result)
    }

fun <T> Flow<NetworkResult<T>>.doOnFailure(action: suspend (Throwable?) -> Unit): Flow<NetworkResult<T>> =
    transform { result ->
        if (result is NetworkResult.Failure) {
            action(result.msg)
        }
        return@transform emit(result)
    }

fun <T> Flow<NetworkResult<T>>.doOnLoading(action: suspend () -> Unit): Flow<NetworkResult<T>> =
    transform { result ->
        if (result is NetworkResult.Loading) {
            action()
        }
        return@transform emit(result)
    }