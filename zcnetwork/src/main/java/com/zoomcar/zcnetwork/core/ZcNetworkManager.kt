package com.zoomcar.zcnetwork.core

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.zoomcar.zcnetwork.listeners.ZcNetworkAnalyticsListener
import com.zoomcar.zcnetwork.models.JavaServiceBaseVO
import com.zoomcar.zcnetwork.models.JavaServiceErrorDetailVO
import com.zoomcar.zcnetwork.utils.ErrorString.SERVER_ERROR
import com.zoomcar.zcnetwork.utils.NetworkResponseStatus.FAILURE
import com.zoomcar.zcnetwork.utils.NetworkResponseStatus.SUCCESS
import com.zoomcar.zcnetwork.utils.getTimeDifferenceInMillis
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*
  * @created 05/01/2020 - 10:21 PM
  * @project ZC-Network-Client
  * @author Paras
  * Copyright (c) 2020 Zoomcar. All rights reserved.
*/
class ZcNetworkManager(
    private val applicationContext: Context
) {
    private var analyticsListener: ZcNetworkAnalyticsListener? = null

    fun initNetworkClient() {
        ZcRequestManager.getInstance(applicationContext)
    }

    fun setNetworkAnalyticsListener(analyticsListener: ZcNetworkAnalyticsListener) {
        this.analyticsListener = analyticsListener
    }

    fun <T> request(
        fragment: Fragment? = null,
        activity: Activity? = null,
        requestCode: Int = -1,
        headerParams: HashMap<String, String>? = null,
        params: HashMap<String, Any>? = null,
        listener: ZcNetworkListener<T>? = null,
        tag: String? = null,
        url: String? = null
    ) {
        fun isComponentAdded() = (activity == null || !activity.isFinishing
                && (fragment == null || fragment.isAdded))

        val reqStartTime = System.currentTimeMillis()
        fun invokeResponseTime(responseStatus: String) {
            analyticsListener?.responseTimeEvent(
                reqStartTime.getTimeDifferenceInMillis(),
                responseStatus
            )
        }

        val callback = object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    invokeResponseTime(SUCCESS)
                    if (isComponentAdded()) {
                        listener?.onSuccess(response.body(), 0)
                    }
                } else {
                    invokeResponseTime(FAILURE)
                    response.errorBody()?.let {
                        return try {
                            when (listener) {
                                is ZcJavaServiceNetworkListener -> {
                                    handleJavaServiceNetworkError(
                                        listener,
                                        response,
                                        isComponentAdded()
                                    )
                                }
                                else -> {
                                    handleNetworkError(listener, response, isComponentAdded())
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                invokeResponseTime(FAILURE)
            }
        }
    }

    private fun <T> handleJavaServiceNetworkError(
        listener: ZcJavaServiceNetworkListener<T>,
        response: Response<T>,
        componentAdded: Boolean
    ) {
        val networkError = listener.buildJavaServiceNetworkError(
            response.code(), response.errorBody()?.bytes()!!
        )
        if (networkError.error == null) networkError.error = JavaServiceBaseVO()
        if (networkError.error?.details == null) {
            networkError.error?.details = JavaServiceErrorDetailVO(message = SERVER_ERROR)
        }
        networkError.error?.code?.run { analyticsListener?.javaServiceFailureEvent(networkError) }
        if (componentAdded) listener.onJavaServiceNetworkError(networkError)
    }

    private fun <T> handleNetworkError(
        listener: ZcNetworkListener<T>?,
        response: Response<T>,
        componentAdded: Boolean
    ) {
        val networkError = listener?.buildNetworkError(
            response.code(), response.errorBody()?.bytes()!!
        )
        networkError?.let {
            analyticsListener?.failureEvent(it)
            if (componentAdded) listener.onError(it)
        }
    }
}