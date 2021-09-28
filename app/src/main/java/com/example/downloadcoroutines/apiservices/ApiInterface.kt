package com.nexogic.apiservices

import com.example.downloadcoroutines.modelClasses.PicsModel

import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {
    //    <---------------Authentication Apis---------------------->

    companion object
    {
        const val BASE_URL = "https://picsum.photos/"
    }
    @GET("v2/list")
    suspend fun getPics(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): ArrayList<PicsModel>

}