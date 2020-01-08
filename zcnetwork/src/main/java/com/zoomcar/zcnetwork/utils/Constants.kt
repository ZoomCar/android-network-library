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

object RetrofitConstants {
    const val BASE_URL = "https://api.zoomcar.com"
}

object DefaultParams {
    const val ANDROID = "android"
    const val CITY = "city"
    const val DEVICE_ID = "device_id"
    const val DEVICE_NAME = "device_name"
    const val PLATFORM = "platform"
    const val VERSION = "version"
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