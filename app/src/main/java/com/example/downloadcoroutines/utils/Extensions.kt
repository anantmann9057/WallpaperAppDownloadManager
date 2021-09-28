package com.example.downloadcoroutines.utils

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView


const val MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1
val WEB_LINK = "WEB_LINK"
internal fun Context.showToast(mssg: Any) {
    Toast.makeText(this, mssg.toString(), Toast.LENGTH_SHORT).show()
}

internal fun getDominantColor(bitmap: Bitmap?): Int {
    val newBitmap = Bitmap.createScaledBitmap(bitmap!!, 1, 1, true)
    val color = newBitmap.getPixel(0, 0)
    newBitmap.recycle()
    return color
}

internal fun Context.showLog(mssg: Any) {
    Log.e("Logger:", mssg.toString())
}
