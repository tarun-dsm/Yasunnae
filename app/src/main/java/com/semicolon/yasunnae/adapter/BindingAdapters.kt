package com.semicolon.yasunnae.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import java.io.File

object BindingAdapters {

    @BindingAdapter("imageURL")
    @JvmStatic
    fun loadImage(imageView: ImageView, image: File) {
        Picasso.get().load(image).into(imageView)
    }
}