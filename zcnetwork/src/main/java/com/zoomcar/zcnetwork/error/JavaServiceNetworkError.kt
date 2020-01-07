package com.zoomcar.zcnetwork.error

import android.os.Parcelable
import com.zoomcar.zcnetwork.models.JavaServiceBaseVO
import com.zoomcar.zcnetwork.utils.ErrorCode.CUSTOM_SERVER_ERROR
import com.zoomcar.zcnetwork.utils.ErrorCode.SERVER_ERROR
import kotlinx.android.parcel.Parcelize

/*
 * @created 05/01/2020 - 1:00 AM
 * @project ZC-Network-Client
 * @author Paras
 * Copyright (c) 2020 . All rights reserved.
 */
@Parcelize
class JavaServiceNetworkError constructor(
    var error: JavaServiceBaseVO? = null,
    var httpCode: Int

) : Parcelable {
    init {
        if (httpCode == SERVER_ERROR) {
            error?.details?.errorCode = CUSTOM_SERVER_ERROR
        }
    }
}