package com.zoomcar.zcnetwork.error

import android.os.Parcelable
import com.zoomcar.zcnetwork.models.BaseErrorVO
import com.zoomcar.zcnetwork.utils.ErrorCode.SERVER_ERROR
import kotlinx.android.parcel.Parcelize

/*
 * @created 05/01/2020 - 1:05 AM
 * @project ZC-Network-Client
 * @author Paras
 * Copyright (c) 2020 Zoomcar. All rights reserved.
 */
@Parcelize
class NetworkError constructor(
    var httpCode: Int,
    var error: BaseErrorVO? = null
) : Parcelable {
    init {
        if (httpCode == SERVER_ERROR) {

        }
    }
}