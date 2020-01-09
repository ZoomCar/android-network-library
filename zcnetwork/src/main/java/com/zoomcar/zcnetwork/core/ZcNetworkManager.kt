package com.zoomcar.zcnetwork.core

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.google.gson.JsonElement
import com.zoomcar.zcnetwork.error.JavaServiceNetworkError
import com.zoomcar.zcnetwork.error.NetworkError
import com.zoomcar.zcnetwork.listeners.ZcNetworkAnalyticsListener
import com.zoomcar.zcnetwork.models.JavaServiceBaseVO
import com.zoomcar.zcnetwork.models.JavaServiceErrorDetailVO
import com.zoomcar.zcnetwork.utils.CustomExceptions
import com.zoomcar.zcnetwork.utils.CustomExceptions.NOT_INITIALIZED
import com.zoomcar.zcnetwork.utils.ErrorCode.NO_NETWORK
import com.zoomcar.zcnetwork.utils.ErrorString.DEFAULT_RETROFIT_ERROR
import com.zoomcar.zcnetwork.utils.ErrorString.SERVER_ERROR
import com.zoomcar.zcnetwork.utils.NetworkResponseStatus.FAILURE
import com.zoomcar.zcnetwork.utils.NetworkResponseStatus.SUCCESS
import com.zoomcar.zcnetwork.utils.ZcRequestType
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
object ZcNetworkManager {
    private var analyticsListener: ZcNetworkAnalyticsListener? = null
    private lateinit var applicationContext: Context
    private var zcRequestManager: ZcRequestManager? = null
    private var isDebugLogEnabled: Boolean = false
    private var baseUrl: String? = null

    fun builder(applicationContext: Context): ZcNetworkManager =
        apply { this.applicationContext = applicationContext }

    fun setDebugLog(isDebugLogEnabled: Boolean): ZcNetworkManager =
        apply { this.isDebugLogEnabled = isDebugLogEnabled }

    fun setNetworkAnalyticsListener(analyticsListener: ZcNetworkAnalyticsListener): ZcNetworkManager =
        apply {
            this.analyticsListener = analyticsListener
        }

    fun addBaseUrl(baseUrl: String?): ZcNetworkManager = apply { this.baseUrl = baseUrl }

    fun build() {
        if (this.baseUrl == null) throw java.lang.IllegalArgumentException(
            CustomExceptions.NO_BASE_URL
        )
        zcRequestManager =
            ZcRequestManager.getInstance(applicationContext, isDebugLogEnabled, baseUrl!!)
    }

    fun request(
        fragment: Fragment? = null,
        activity: Activity? = null,
        requestCode: Int = -1,
        requestType: ZcRequestType,
        headerParams: HashMap<String, String>? = null,
        params: HashMap<String, Any>? = null,
        listener: ZcNetworkListener? = null,
        tag: String? = null,
        url: String,
        defaultService: Boolean = true,
        baseUrl: String? = null
    ) {

        if (zcRequestManager == null) throw IllegalArgumentException(NOT_INITIALIZED)

        addHeaderParams(headerParams, activity!!)

        fun isComponentAdded() = (activity == null || !activity.isFinishing
                && (fragment == null || fragment.isAdded))

        val reqStartTime = System.currentTimeMillis()
        fun invokeTimingEvent(responseStatus: String) {
            analyticsListener?.responseTimeEvent(
                reqStartTime.getTimeDifferenceInMillis(),
                responseStatus,
                requestCode,
                tag
            )
        }

        val callback = object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                if (response.isSuccessful) {
                    invokeTimingEvent(SUCCESS)
                    if (isComponentAdded()) {
                        listener?.onSuccess(response.body()?.asJsonObject, 0)
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

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
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
        val call: Call<JsonElement>?
        if (defaultService) {
            val apiService =
                ZcRequestManager.getInstance(activity, baseUrl = this.baseUrl!!)
                    .getDefaultApiService()
            call = when (requestType) {
                ZcRequestType.GET -> apiService.getResource(url, hashMapOf("key" to "value"))
                ZcRequestType.POST -> apiService.createResource(url, params)
                ZcRequestType.PUT -> apiService.updateResource(url, params)
                ZcRequestType.PATCH -> apiService.patchResource(url, params)
                ZcRequestType.DELETE -> apiService.deleteResource(url, params)
            }
            call.run { call.enqueue(callback) }
        }
    }

    private fun addHeaderParams(
        headerParams: java.util.HashMap<String, String>?,
        activity: Activity
    ) {
        if (headerParams != null) {
            ZcRequestManager.getInstance(activity, baseUrl = baseUrl!!)
                .setHeaderParams(headerParams)
        }
    }

    private fun handleJavaServiceNetworkError(
        networkError: JavaServiceNetworkError,
        listener: ZcJavaServiceNetworkListener,
        componentAdded: Boolean
    ) {
        if (networkError.error == null) networkError.error = JavaServiceBaseVO()
        if (networkError.error?.details == null) {
            networkError.error?.details = JavaServiceErrorDetailVO(message = SERVER_ERROR)
        }
        networkError.error?.code?.run { analyticsListener?.javaServiceFailureEvent(networkError) }
        if (componentAdded) listener.onJavaServiceNetworkError(networkError)
    }

    private fun handleNetworkError(
        networkError: NetworkError?,
        listener: ZcNetworkListener?,
        componentAdded: Boolean
    ) {
        networkError?.let {
            analyticsListener?.failureEvent(it)
            if (componentAdded) listener?.onError(it)
        }
    }
}