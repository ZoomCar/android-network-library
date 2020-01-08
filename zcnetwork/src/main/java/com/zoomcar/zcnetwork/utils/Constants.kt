package com.zoomcar.zcnetwork.utils

object ErrorCode {
    const val NO_NETWORK = 0
    const val SERVER_ERROR = 500
    const val CUSTOM_SERVER_ERROR = 1003
}

object NetworkResponseStatus {
    const val SUCCESS = "SUCCESS"
    const val FAILURE = "FAILURE"
}

object ErrorString {
    const val SERVER_ERROR = "Something went wrong. Please try again later."
    const val DEFAULT_RETROFIT_ERROR = "Default Retrofit error"
}