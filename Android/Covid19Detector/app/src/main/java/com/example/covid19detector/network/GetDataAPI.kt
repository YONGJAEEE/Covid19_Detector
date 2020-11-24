package com.example.covid19detector.network

import com.example.covid19detector.model.CovidResponse
import com.example.covid19detector.model.JusoResponse
import com.example.covid19detector.model.PostBody
import com.example.covid19detector.model.PostResponse
import retrofit2.Call
import retrofit2.http.*

interface GetDataAPI {
    @GET("address.json")
    fun getXYByJuso(
        @Header("Authorization") KakaoAK: String = "KakaoAK 1e0abb48f975bdcc91c3edeafa42dad7",
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("query") query: String
    ): Call<JusoResponse>

    @GET("status")
    fun getTotalCovid() : Call<CovidResponse>

    @POST("location")
    fun postLatLon(
        @Body postBody : PostBody
    ):Call<PostResponse>
}