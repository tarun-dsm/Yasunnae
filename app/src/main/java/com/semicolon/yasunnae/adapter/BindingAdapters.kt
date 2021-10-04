package com.semicolon.yasunnae.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation

object BindingAdapters {

    @BindingAdapter("imageURL")
    @JvmStatic
    fun loadImage(imageView: ImageView, imageURL: String) {
        Picasso.get().load(imageURL).into(imageView)
    }
}