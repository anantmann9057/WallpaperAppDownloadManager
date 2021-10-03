package com.example.downloadcoroutines.apiservices

import com.example.downloadcoroutines.modelClasses.PicsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {
    companion object {
        const val BASE_URL = "https://picsum.photos/"
    }

    @GET("v2/list")
    suspend fun getPics(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): ArrayList<PicsModel>

    @GET("/list")
    suspend fun getAltPics(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<ArrayList<PicsModel>>

}