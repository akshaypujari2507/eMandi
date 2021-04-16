package com.emandi.data.remote.api

import com.emandi.utils.Util
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private val client = OkHttpClient.Builder()
        .build()

    val api = Retrofit.Builder()
        .baseUrl(Util.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build().create(ApiService::class.java)!!

}
