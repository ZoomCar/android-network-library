package com.zoomcar.zcnetwork.utils

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings

/*
  * @created 08/01/2020 - 11:29 AM
  * @project ZC-Network-Client
  * @author Paras
  * Copyright (c) 2020 Zoomcar. All rights reserved.
*/
@SuppressLint("HardwareIds")
fun getDeviceId(context: Context): String =
    Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)