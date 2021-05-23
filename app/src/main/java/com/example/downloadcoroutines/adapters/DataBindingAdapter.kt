package com.nexogic.adapters


import android.view.View
import androidx.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.downloadcoroutines.R
import jp.wasabeef.glide.transformations.BlurTransformation


class DataBindingAdapter {


    companion object {

        @JvmStatic
        @BindingAdapter("app:src")
        fun setSrc(imageView: ImageView?, imageURL: String?) {
            imageView?.scaleType = ImageView.ScaleType.FIT_XY
            if (imageURL != null) {

                Glide.with(imageView!!.context)
                    .load(imageURL)
                    .centerInside()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .thumbnail(
                        Glide.with(imageView.context).load(R.drawable.progress_animation)
                            .thumbnail(0.1f)
                    )
                    .into(imageView)

            }


        }

        @JvmStatic
        @BindingAdapter("app:blurred")
        fun setBlurred(imageView: ImageView?, imageURL: String?) {
            imageView?.scaleType = ImageView.ScaleType.FIT_XY
            if (imageURL != null) {
                Glide.with(imageView!!.context)
                    .load(imageURL)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3)))
                    .thumbnail(
                        Glide.with(imageView.context).load(R.drawable.progress_animation)
                            .thumbnail(0.1f)
                    )
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