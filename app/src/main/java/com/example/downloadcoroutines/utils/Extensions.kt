package com.example.downloadcoroutines

import android.content.Context
import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1
internal fun Context.showToast(mssg: Any) {
    Toast.makeText(this, mssg.toString(), Toast.LENGTH_SHORT).show()
}

internal fun Context.showLog(mssg: Any) {
    Log.e("Logger:", mssg.toString())
}

internal fun <T> Call<T>.enqueue(callback: CallBackKt<T>.() -> Unit) {
    val callBackKt = CallBackKt<T>()
    callback.invoke(callBackKt)
    this.enqueue(callBackKt)
}

internal class CallBackKt<T> : Callback<T> {

    var onResponse: ((Response<T>) -> Unit)? = null
    var onFailure: ((t: Throwable?) -> Unit)? = null

    override fun onFailure(
        call: Call<T>,
        t: Throwable
    ) {
        onFailure?.invoke(t)
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        onResponse?.invoke(response)
    }

}