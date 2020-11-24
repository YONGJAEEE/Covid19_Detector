package com.example.covid19detector.network

import com.example.covid19detector.model.JusoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GetDataAPI {
    @GET("address.json")
    fun getXYByJuso(
        @Header("Authorization") KakaoAK: String = "KakaoAK 1e0abb48f975bdcc91c3edeafa42dad7",
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("query") query: String
    ): Call<JusoResponse>
}