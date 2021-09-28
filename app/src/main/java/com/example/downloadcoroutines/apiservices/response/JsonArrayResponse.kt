package com.example.downloadcoroutines.apiservices.response

import com.google.gson.annotations.SerializedName

class JsonArrayResponse<T> : BaseResponse() {
    @SerializedName("data")
    var list: List<T>? = null
}