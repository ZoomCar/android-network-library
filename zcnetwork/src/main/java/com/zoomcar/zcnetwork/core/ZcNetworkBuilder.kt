package com.zoomcar.zcnetwork.core

import android.app.Activity
import androidx.fragment.app.Fragment
import com.zoomcar.zcnetwork.utils.ZcRequestType

/*
  * @created 08/01/2020 - 12:35 PM
  * @project ZC-Network-Client
  * @author Paras
  * Copyright (c) 2020 Zoomcar. All rights reserved.
*/
class ZcNetworkBuilder {
    private var activity: Activity? = null
    private var fragment: Fragment? = null
    private var requestCode: Int = 0
    private var headerParams: HashMap<String, String>? = null
    private var requestParams: HashMap<String, Any> = hashMapOf()
    private var bodyParams: HashMap<String, Any> = hashMapOf()
    private var listener: ZcNetworkListener? = null
    private var tag: String? = null
    private lateinit var url: String
    private lateinit var requestType: ZcRequestType
    private var useDefaultService: Boolean = true
    private var baseUrl: String? = null

    fun setActivity(activity: Activity): ZcNetworkBuilder = apply { this.activity = activity }
    fun setFragment(fragment: Fragment): ZcNetworkBuilder = apply { this.fragment = fragment }
    fun setRequestCode(requestCode: Int): ZcNetworkBuilder =
        apply { this.requestCode = requestCode }

    fun setHeaderParams(headerParams: HashMap<String, String>): ZcNetworkBuilder =
        apply { this.headerParams = headerParams }

    fun setRequestParams(requestParams: HashMap<String, Any>): ZcNetworkBuilder =
        apply { this.requestParams = requestParams }

    fun setBodyParams(bodyParams: HashMap<String, Any>): ZcNetworkBuilder =
        apply { this.bodyParams = bodyParams }

    fun setListener(listener: ZcNetworkListener): ZcNetworkBuilder =
        apply { this.listener = listener }

    fun setTag(tag: String): ZcNetworkBuilder = apply { this.tag = tag }
    fun setUrl(url: String): ZcNetworkBuilder = apply { this.url = url }
    fun setRequestType(requestType: ZcRequestType) = apply { this.requestType = requestType }
    fun setBaseUrl(baseUrl: String) = apply { this.baseUrl = baseUrl }

    fun request() {
        ZcNetworkManager.request(
            fragment,
            activity,
            requestCode,
            requestType,
            headerParams,
            requestParams,
            listener,
            tag,
            url,
            useDefaultService,
            bodyParams
        )
    }
}