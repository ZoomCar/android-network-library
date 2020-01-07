package com.zoomcar.zcnetwork.utils

import com.zoomcar.zcnetwork.error.JavaServiceNetworkError
import com.zoomcar.zcnetwork.error.NetworkError

sealed class ApiResource<T>(
    val requestCode: Int,
    val data: T? = null,
    val javaServiceNetworkError: JavaServiceNetworkError? = null,
    val networkError: NetworkError? = null
) {
    class Success<T>(requestCode: Int, data: T?) : ApiResource<T>(requestCode, data)
    class Error<T>(
        requestCode: Int,
        javaServiceNetworkError: JavaServiceNetworkError? = null,
        networkError: NetworkError? = null
    ) : ApiResource<T>(
        requestCode,
        javaServiceNetworkError = javaServiceNetworkError,
        networkError = networkError
    )

    class Loading<T>(requestCode: Int) : ApiResource<T>(requestCode)
}