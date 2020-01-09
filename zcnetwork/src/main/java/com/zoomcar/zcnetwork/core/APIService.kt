package com.zoomcar.zcnetwork.core

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.*

/*
 * @created 05/01/2020 - 1:20 AM
 * @project ZC-Network-Client
 * @author Paras
 * Copyright (c) 2020 Zoomcar. All rights reserved.
 */
interface ZcApiService {

    @GET
    fun getResource(@Url url: String, @QueryMap query: HashMap<String, Any>?): Call<JsonElement>

    @PUT
    fun updateResource(@Url url: String, @QueryMap query: HashMap<String, Any>?): Call<JsonElement>

    @POST
    fun createResource(@Url url: String, @QueryMap query: HashMap<String, Any>?): Call<JsonElement>

    @POST
    fun createResourceWithBody(@Url url: String, @Body query: HashMap<String, Any>?): Call<JsonElement>

    @DELETE
    fun deleteResource(@Url url: String, @QueryMap query: HashMap<String, Any>?): Call<JsonElement>

    @PATCH
    fun patchResource(@Url url: String, @QueryMap query: HashMap<String, Any>?): Call<JsonElement>
}