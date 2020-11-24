package com.example.covid19detector.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiRetrofitClient {
    private var instance: Retrofit? = null

    val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl("http://121.152.10.41:4000/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val GetData : GetDataAPI = retrofit.create(GetDataAPI::class.java)

    companion object {
        val instance = ApiRetrofitClient()
    }
}