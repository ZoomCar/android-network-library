package com.zoomcar.zcnetwork.core

import android.content.Context

/*
  * @created 05/01/2020 - 10:19 PM
  * @project ZC-Network-Client
  * @author Paras
  * Copyright (c) 2020 Zoomcar. All rights reserved.
*/
class ZcRequestManager(
    private val applicationContext: Context
) {

    companion object {
        @Volatile
        private var instance: ZcRequestManager? = null

        fun getInstance(applicationContext: Context) =
            instance ?: synchronized(applicationContext) {
                instance ?: ZcRequestManager(applicationContext).also { instance = it }
            }
    }
}