package com.example.covid19detector.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiRetrofitClient {
    private var instance: Retrofit? = null

    val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl("http://172.30.1.50:8080/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val GetData : GetDataAPI = retrofit.create(GetDataAPI::class.java)

    companion object {
        val instance = ApiRetrofitClient()
    }
}