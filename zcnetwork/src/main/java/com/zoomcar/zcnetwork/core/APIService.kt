package com.zoomcar.zcnetwork.core

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import java.util.*
import kotlin.collections.HashMap

/*
 * @created 05/01/2020 - 1:20 AM
 * @project ZC-Network-Client
 * @author Paras
 * Copyright (c) 2020 Zoomcar. All rights reserved.
 */
interface ZcApiService {

    @GET
    fun getResource(@Url url: String, @QueryMap query: HashMap<String, Any>): Call<ResponseBody>

    @PUT
    fun updateResource(@Url url: String, @QueryMap query: HashMap<String, Any>): Call<ResponseBody>

    @POST
    fun createResource(@Url url: String, @QueryMap query: HashMap<String, Any>): Call<ResponseBody>

    @DELETE
    fun deleteResource(@Url url: String, @QueryMap query: HashMap<String, Any>): Call<ResponseBody>

    @PATCH
    fun patchResource(@Url url: String, @QueryMap query: HashMap<String, Any>): Call<ResponseBody>
}