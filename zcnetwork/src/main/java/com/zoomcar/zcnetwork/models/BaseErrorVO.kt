package com.zoomcar.zcnetwork.models

import android.os.Parcelable
import com.bluelinelabs.logansquare.annotation.JsonField
import com.bluelinelabs.logansquare.annotation.JsonObject
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
@JsonObject
open class BaseErrorVO : Parcelable {
    @JsonField
    @SerializedName(value = "status")
    var status: Int = 0

    @SerializedName(value = "error_code")
    @JsonField(name = ["error_code"])
    var errorCode: Int = 0

    @SerializedName(value = "error_title")
    @JsonField(name = ["error_title"])
    var errorTitle: String? = null

    @SerializedName("msg")
    @JsonField
    var msg: String? = null

    @JsonField
    @SerializedName("httpStatusCode")
    var httpStatusCode: Int = 0

    @JsonField(name = ["metadata"])
    @SerializedName("metadata")
    var metadata: MutableMap<String, @RawValue Any?>? = null
}