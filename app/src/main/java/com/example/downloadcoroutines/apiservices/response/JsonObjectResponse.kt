package com.example.downloadcoroutines.apiservices.response

import com.google.gson.annotations.SerializedName

class JsonObjectResponse<T> : BaseResponse() {
    @SerializedName("data")
    var `object`: T? = null
}