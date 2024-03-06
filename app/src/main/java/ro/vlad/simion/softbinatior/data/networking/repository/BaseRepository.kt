package ro.vlad.simion.softbinatior.data.networking.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import ro.vlad.simion.softbinatior.data.networking.model.NetworkResult
import java.io.IOException

abstract class BaseRepository {
    fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        apiCall: suspend () -> Response<T>
    ): Flow<NetworkResult<T>> = flow {
        emit(NetworkResult.Loading)
        val response = apiCall()
        if (response.isSuccessful) {
            val data = response.body()
            if (data != null) {
                emit(NetworkResult.Success(data))
            } else {
                val error = response.errorBody()
                if (error != null) {
                    emit(NetworkResult.Failure(IOException(error.toString())))
                } else {
                    emit(NetworkResult.Failure(IOException("something went wrong")))
                }
            }
        } else {
            emit(NetworkResult.Failure(Throwable(response.raw().toString())))
        }
    }.catch { e ->
        e.printStackTrace()
        emit(NetworkResult.Failure(Exception(e)))
    }.flowOn(dispatcher)

}