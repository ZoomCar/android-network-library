package com.zoomcar.networkclient

import android.app.Application
import com.zoomcar.zcnetwork.core.ZcNetworkManager

/*
  * @created 08/01/2020 - 5:35 PM
  * @project ZC-Network-Client
  * @author Paras
  * Copyright (c) 2020 Zoomcar. All rights reserved.
*/
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ZcNetworkManager.initNetworkClient(
            this
        )
    }
}