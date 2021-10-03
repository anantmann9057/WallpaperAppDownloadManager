package com.example.downloadcoroutines.utils

import kotlinx.coroutines.flow.*

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {
    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(NetworkResource.Loading(data))

        try {
            saveFetchResult(fetch())
            query().map { NetworkResource.Success(it) }

        } catch (throwable: Throwable) {
            query().map { NetworkResource.Error(throwable, it) }
        }
    } else {
        query().map { NetworkResource.Success(it) }
    }

    emitAll(flow)

}
