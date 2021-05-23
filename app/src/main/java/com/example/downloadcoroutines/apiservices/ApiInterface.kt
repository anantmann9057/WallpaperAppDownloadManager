package com.nexogic.apiservices

import com.example.downloadcoroutines.modelClasses.SpecialistsModel

import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {
    //    <---------------Authentication Apis---------------------->

    @GET("v2/list")
    fun getPics(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Call<ArrayList<SpecialistsModel>>

}