package com.nexogic.apiservices.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class BaseResponse {

    @SerializedName("response")
    @Expose
    val response: String? = null

    @SerializedName("status")
    @Expose
    val status: Int? = null

    @SerializedName("message")
    @Expose
    val message: String? = null

    @SerializedName("token")
    @Expose
    val token: String? = null

    @SerializedName("method_name")
    @Expose
    val methodName: String? = null

    @SerializedName("total_page")
    @Expose
    val totalPage = 0

    @SerializedName("otp")
    @Expose
    val otp = ""

}