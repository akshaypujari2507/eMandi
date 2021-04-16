package com.emandi.data.remote.api

import com.emandi.data.remote.model.ApiResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("get-products")
    fun getResponse(): Call<ApiResponse>

}