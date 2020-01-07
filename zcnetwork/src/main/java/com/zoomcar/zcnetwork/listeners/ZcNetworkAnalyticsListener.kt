package com.zoomcar.zcnetwork.listeners

import com.zoomcar.zcnetwork.error.JavaServiceNetworkError
import com.zoomcar.zcnetwork.error.NetworkError

/*
  * @created 07/01/2020 - 12:20 PM
  * @project ZC-Network-Client
  * @author Paras
  * Copyright (c) 2020 Zoomcar. All rights reserved.
*/
interface ZcNetworkAnalyticsListener {
    fun responseTimeEvent(timeDiff: Long, status: String)
    fun failureEvent(error: NetworkError)
    fun javaServiceFailureEvent(error: JavaServiceNetworkError)
}