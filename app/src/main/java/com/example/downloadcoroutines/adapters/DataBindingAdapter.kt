package com.example.downloadcoroutines.adapters


import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.downloadcoroutines.R
import com.example.downloadcoroutines.utils.getDominantColor
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.StyledPlayerView
import jp.wasabeef.glide.transformations.BlurTransformation


class DataBindingAdapter {


    companion object {

        @JvmStatic
        @BindingAdapter("app:src")
        fun setSrc(imageView: ImageView?, imageURL: String?) {
            if (imageURL != null) {
                Glide.with(imageView!!.context)
                    .load(imageURL)
                    .centerInside()
                    .error(R.drawable.ic_house)
                    .apply(RequestOptions().override(600, 800))
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .thumbnail(
                        Glide.with(imageView.context)
                            .load(R.drawable.progress_animation)
                            .thumbnail(0.1f)
                    )
                    .into(imageView)

            }


        }


        @JvmStatic
        @BindingAdapter("app:placeholder")
        fun setPlaceholder(animationView: LottieAnimationView, imageURL: String) {
            animationView.setAnimationFromUrl(imageURL)

        }

        @JvmStatic
        @BindingAdapter("filter")
        fun setFilter(imageView: ImageView, imageURL: String?) {
            Glide.with(imageView.context)
                .asBitmap()
                .load(imageURL)
                .centerInside()
                .apply(RequestOptions().override(900, 1080))
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        imageView.setImageBitmap(resource)
                        imageView.setColorFilter(
                            getDominantColor(resource),
                            PorterDuff.Mode.DARKEN
                        )
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        // this is called when imageView is cleared on lifecycle call or for
                        // some other reason.
                        // if you are referencing the bitmap somewhere else too other than this imageView
                        // clear it here as you can no longer have the bitmap
                    }
                })
        }

        @JvmStatic
        @BindingAdapter("app:blurred")
        fun setBlurred(imageView: ImageView?, imageURL: String?) {
            if (imageURL != null) {
                Glide.with(imageView!!.context)
                    .load(imageURL)
                    .fitCenter()
                    .apply(RequestOptions().override(1440, 800))
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3)))
                    .into(imageView)

            }

        }

        @JvmStatic
        @BindingAdapter("app:viewVisibility")
        fun setViewVisibility(view: View, isShown: Boolean) {
            if (isShown) {
                view.visibility = View.VISIBLE
            } else {
                view.visibility = View.GONE
            }
        }
    }
}