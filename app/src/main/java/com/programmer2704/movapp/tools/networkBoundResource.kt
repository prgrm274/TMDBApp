package com.programmer2704.movapp.tools

import kotlinx.coroutines.flow.*

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String?, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}
inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: ()-> Flow<ResultType>,
    crossinline fetch: suspend ()-> RequestType,
    crossinline saveFetchResult: suspend (RequestType)-> Unit,
    crossinline onFetchFailed: (Throwable) -> Unit = { },
    crossinline shouldFetch: (ResultType)-> Boolean = { true }
) = flow<Resource<ResultType>> {
    emit(Resource.loading(null))
    val data = query().first()

    val floww = if (shouldFetch(data)) {
        emit(Resource.loading(data))

        try {
            saveFetchResult(fetch())
            query().map { Resource.success(it) }
        } catch (throwable: Throwable) {
            onFetchFailed(throwable)
            query().map { Resource.error(throwable.message, it) }
        }
    } else {
        query().map { Resource.success(it) }
    }
    emitAll(floww)
}