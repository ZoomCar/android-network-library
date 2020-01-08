package com.zoomcar.zcnetwork.core

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
    fun <T> getResource(@Url url: String, @QueryMap query: HashMap<String, Any>?): Call<T>

    @PUT
    fun <T> updateResource(@Url url: String, @QueryMap query: HashMap<String, Any>?): Call<T>

    @POST
    fun <T> createResource(@Url url: String, @QueryMap query: HashMap<String, Any>?): Call<T>

    @DELETE
    fun <T> deleteResource(@Url url: String, @QueryMap query: HashMap<String, Any>?): Call<T>

    @PATCH
    fun <T> patchResource(@Url url: String, @QueryMap query: HashMap<String, Any>?): Call<T>
}