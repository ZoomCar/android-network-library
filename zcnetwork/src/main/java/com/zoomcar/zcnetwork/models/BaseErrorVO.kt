package com.zoomcar.zcnetwork.models

import android.os.Parcelable
import com.bluelinelabs.logansquare.annotation.JsonField
import com.bluelinelabs.logansquare.annotation.JsonObject
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonObject
open class BaseErrorVO : Parcelable {
    @JsonField
    var status: Int = 0
    @JsonField(name = ["error_code"])
    var errorCode: Int = 0
    @JsonField(name = ["error_title"])
    var errorTitle: String? = null
    @JsonField
    var msg: String? = null
    @JsonField
    var httpStatusCode: Int = 0
}