package com.zoomcar.zcnetwork.core

import com.bluelinelabs.logansquare.LoganSquare
import com.zoomcar.zcnetwork.error.JavaServiceNetworkError
import com.zoomcar.zcnetwork.models.JavaServiceBaseVO
import com.zoomcar.zcnetwork.utils.ErrorCode.NO_NETWORK

/*
  * @created 07/01/2020 - 12:10 PM
  * @project ZC-Network-Client
  * @author Paras
  * Copyright (c) 2020 Zoomcar. All rights reserved.
*/
interface ZcJavaServiceNetworkListener<T> : ZcNetworkListener<T> {
    fun onJavaServiceNetworkError(javaServiceNetworkError: JavaServiceNetworkError)

    fun buildJavaServiceNetworkError(httpCode: Int, data: ByteArray): JavaServiceNetworkError {
        val javaServiceBaseVO: JavaServiceBaseVO
        return try {
            javaServiceBaseVO = LoganSquare.parse(String(data), JavaServiceBaseVO::class.java)
            JavaServiceNetworkError(javaServiceBaseVO, httpCode)
        } catch (e: Exception) {
            e.printStackTrace()
            JavaServiceNetworkError(NO_NETWORK)
        }
    }
}