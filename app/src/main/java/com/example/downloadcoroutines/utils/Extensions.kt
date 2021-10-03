package com.example.downloadcoroutines.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.downloadcoroutines.R
import com.example.downloadcoroutines.adapters.GenericAdapter
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.StyledPlayerView


const val MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1
const val WEB_LINK = "WEB_LINK"
const val CAMERA_REQUEST_CODE = 200

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

internal fun ImageView.setUri(uri: Uri) {
    Glide.with(context)
        .load(uri)
        .centerInside()
        .apply(RequestOptions().override(4000, 4000))
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .thumbnail(
            Glide.with(context)
                .load(R.drawable.progress_animation)
                .thumbnail(0.1f)
        )
        .into(this)

}

internal fun StyledPlayerView.loadUrl(url: String) {
    var exoPlayer = SimpleExoPlayer.Builder(this.context).build()
    this.player = exoPlayer
    exoPlayer.setMediaItem(MediaItem.fromUri(url))
    exoPlayer.setMediaItem(MediaItem.fromUri(url))
    exoPlayer.prepare()
    exoPlayer.play()
}

