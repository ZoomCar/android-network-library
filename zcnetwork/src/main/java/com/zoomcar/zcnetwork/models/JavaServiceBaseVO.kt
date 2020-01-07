package com.zoomcar.zcnetwork.models

import android.os.Parcelable
import com.bluelinelabs.logansquare.annotation.JsonField
import com.bluelinelabs.logansquare.annotation.JsonObject
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonObject
data class JavaServiceBaseVO(
    @JsonField
    var code: String? = null,
    @JsonField
    var details: JavaServiceErrorDetailVO? = null
) : Parcelable, BaseErrorVO()

@Parcelize
@JsonObject
data class JavaServiceErrorDetailVO(
    @JsonField
    var errorCode: Int = 0,
    @JsonField
    var message: String? = null,
    @JsonField
    var title: String? = null,
    @JsonField
    var charge: Int = 0,
    @JsonField
    var tripUUID: String? = null,
    @JsonField
    var walletBalance: Double = 0.0,
    @JsonField
    var confirmationKey: String? = null
) : Parcelable