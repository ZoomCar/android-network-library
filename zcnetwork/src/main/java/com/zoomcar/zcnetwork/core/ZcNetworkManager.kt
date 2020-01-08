package com.zoomcar.zcnetwork.core

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.zoomcar.zcnetwork.error.JavaServiceNetworkError
import com.zoomcar.zcnetwork.error.NetworkError
import com.zoomcar.zcnetwork.listeners.ZcNetworkAnalyticsListener
import com.zoomcar.zcnetwork.models.JavaServiceBaseVO
import com.zoomcar.zcnetwork.models.JavaServiceErrorDetailVO
import com.zoomcar.zcnetwork.utils.ErrorCode.NO_NETWORK
import com.zoomcar.zcnetwork.utils.ErrorString.DEFAULT_RETROFIT_ERROR
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
        fun invokeTimingEvent(responseStatus: String) {
            analyticsListener?.responseTimeEvent(
                reqStartTime.getTimeDifferenceInMillis(),
                responseStatus
            )
        }

        val callback = object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    invokeTimingEvent(SUCCESS)
                    if (isComponentAdded()) {
                        listener?.onSuccess(response.body(), 0)
                    }
                } else {
                    invokeTimingEvent(FAILURE)
                    if (response.errorBody() != null) {
                        return try {
                            when (listener) {
                                is ZcJavaServiceNetworkListener -> {
                                    val networkError = listener.buildJavaServiceNetworkError(
                                        response.code(), response.errorBody()?.bytes()!!
                                    )
                                    handleJavaServiceNetworkError(
                                        networkError, listener, isComponentAdded()
                                    )
                                }
                                else -> {
                                    val networkError = listener?.buildNetworkError(
                                        response.code(), response.errorBody()?.bytes()!!
                                    )
                                    handleNetworkError(networkError, listener, isComponentAdded())
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    } else if (isComponentAdded()) {
                        listener?.onError(NetworkError(response.code(), DEFAULT_RETROFIT_ERROR))
                    }
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                invokeTimingEvent(FAILURE)
                when (listener) {
                    is ZcJavaServiceNetworkListener -> {
                        val networkError = JavaServiceNetworkError(httpCode = NO_NETWORK)
                        handleJavaServiceNetworkError(networkError, listener, isComponentAdded())
                    }
                    else -> {
                        val networkError = NetworkError(NO_NETWORK)
                        handleNetworkError(networkError, listener, isComponentAdded())
                    }
                }
            }
        }
    }

    private fun <T> handleJavaServiceNetworkError(
        networkError: JavaServiceNetworkError,
        listener: ZcJavaServiceNetworkListener<T>,
        componentAdded: Boolean
    ) {
        if (networkError.error == null) networkError.error = JavaServiceBaseVO()
        if (networkError.error?.details == null) {
            networkError.error?.details = JavaServiceErrorDetailVO(message = SERVER_ERROR)
        }
        networkError.error?.code?.run { analyticsListener?.javaServiceFailureEvent(networkError) }
        if (componentAdded) listener.onJavaServiceNetworkError(networkError)
    }

    private fun <T> handleNetworkError(
        networkError: NetworkError?,
        listener: ZcNetworkListener<T>?,
        componentAdded: Boolean
    ) {
        networkError?.let {
            analyticsListener?.failureEvent(it)
            if (componentAdded) listener?.onError(it)
        }
    }
}