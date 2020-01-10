package com.zoomcar.networkclient

import android.os.Parcelable
import com.bluelinelabs.logansquare.annotation.JsonField
import com.bluelinelabs.logansquare.annotation.JsonObject
import kotlinx.android.parcel.Parcelize

/*
  * @created 09/01/2020 - 3:00 PM
  * @project ZC-Network-Client
  * @author Paras
*/
@Parcelize
@JsonObject
data class User(
    @JsonField
    var id: Int = -1,
    @JsonField
    var name: String? = null,
    @JsonField
    var username: String? = null,
    @JsonField
    var email: String? = null,
    @JsonField
    var address: AddressVO? = null,
    @JsonField
    var phone: String? = null,
    @JsonField
    var website: String? = null,
    @JsonField
    var company: CompanyVO? = null
) : Parcelable

@Parcelize
@JsonObject
data class AddressVO(
    @JsonField
    var street: String? = null,
    @JsonField
    var suite: String? = null,
    @JsonField
    var city: String? = null,
    @JsonField
    var zipcode: String? = null,
    @JsonField
    var geo: GeoVO? = null
) : Parcelable

@Parcelize
@JsonObject
data class GeoVO(
    @JsonField
    var lat: String? = null,
    @JsonField
    var lng: String? = null
) : Parcelable

@Parcelize
@JsonObject
data class CompanyVO(
    var name: String? = null,
    var catchPhrase: String? = null,
    var bs: String? = null
) : Parcelable