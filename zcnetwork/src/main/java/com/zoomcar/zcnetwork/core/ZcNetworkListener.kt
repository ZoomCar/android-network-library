package com.zoomcar.zcnetwork.core

import com.bluelinelabs.logansquare.LoganSquare
import com.google.gson.JsonElement
import com.zoomcar.zcnetwork.error.NetworkError
import com.zoomcar.zcnetwork.models.BaseErrorVO

/*
  * @created 07/01/2020 - 12:09 PM
  * @project ZC-Network-Client
  * @author Paras
  * Copyright (c) 2020 Zoomcar. All rights reserved.
*/
interface ZcNetworkListener {
    fun onSuccess(response: JsonElement?, responseCode: Int)
    fun onError(error: NetworkError)

    fun buildNetworkError(httpCode: Int, data: ByteArray): NetworkError {
        val baseErrorVO: BaseErrorVO
        return try {
            baseErrorVO = LoganSquare.parse(String(data), BaseErrorVO::class.java)
            NetworkError(httpCode, baseErrorVO)
        } catch (e: Exception) {
            e.printStackTrace()
            NetworkError(0)
        }
    }
}