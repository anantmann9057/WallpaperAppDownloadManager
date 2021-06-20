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
            if (imageURL != null) {

                Glide.with(imageView!!.context)
                    .load(imageURL)
                    .centerInside()
                    .apply(RequestOptions().override(1280,800))
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
        @BindingAdapter("app:blurred")
        fun setBlurred(imageView: ImageView?, imageURL: String?) {
            if (imageURL != null) {
                Glide.with(imageView!!.context)
                    .load(imageURL)
                    .fitCenter()
                    .apply(RequestOptions().override(1440,800))
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