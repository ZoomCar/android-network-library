package com.zoomcar.zcnetwork.error

import android.os.Parcelable
import com.zoomcar.zcnetwork.models.JavaServiceBaseVO
import com.zoomcar.zcnetwork.models.JavaServiceErrorDetailVO
import com.zoomcar.zcnetwork.utils.ErrorCode.CUSTOM_SERVER_ERROR
import com.zoomcar.zcnetwork.utils.ErrorCode.SERVER_ERROR
import com.zoomcar.zcnetwork.utils.ErrorString
import kotlinx.android.parcel.Parcelize

/*
 * @created 05/01/2020 - 1:00 AM
 * @project ZC-Network-Client
 * @author Paras
 * Copyright (c) 2020 . All rights reserved.
 */
@Parcelize
class JavaServiceNetworkError constructor(
    var error: JavaServiceBaseVO?,
    var httpCode: Int

) : Parcelable {

    init {
        if (httpCode == SERVER_ERROR) {
            error?.details?.errorCode = CUSTOM_SERVER_ERROR
        }
    }

    constructor(httpCode: Int) : this(null, httpCode) {
        this.httpCode = httpCode
        this.error = JavaServiceBaseVO()
        this.error?.code = httpCode.toString()
        if (this.error?.details == null) this.error?.details = JavaServiceErrorDetailVO()
        this.error?.details?.message = ErrorString.SERVER_ERROR
    }
}