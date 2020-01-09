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

object TimeoutDefaults {
    const val CONNECT = 30000
    const val READ = 30000
    const val WRITE = 30000
}

object TimeoutHeaders {
    const val CONNECT = "CONNECT_TIMEOUT"
    const val READ = "READ_TIMEOUT"
    const val WRITE = "WRITE_TIMEOUT"
}

object CustomExceptions {
    const val NOT_INITIALIZED = "Network client isn't initialized!"
    const val NO_BASE_URL = "Retrofit BASE URL is required!"
}

object LibTag {
    const val TAG = "ZC Network Client"
}