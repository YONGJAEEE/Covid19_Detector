package com.example.covid19detector.network

import com.example.covid19detector.model.dummy
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetDataAPI {
    @GET("search")
    fun SearchSc(@Query("school_name") name: String): Call<dummy>

}