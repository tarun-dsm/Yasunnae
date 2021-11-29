package com.semicolon.yasunnae.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.semicolon.domain.enum.AnimalType
import com.semicolon.yasunnae.R
import io.reactivex.Single
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

fun String?.toPrettyDate(): String {
    if (this == null) return ""
    val date = this.split("T")
    return if(date.size >= 2) (date[0] + " " + date[1]).replace("-", "/") else ""
}

fun String?.toPrettyDateWithoutTime(): String {
    if (this == null) return ""
    val date = this.split("T")
    return date[0].replace("-", "/")
}

fun String.toDate(): Date =
    SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).parse(this) ?: Date(0)

fun Date.format(pattern: String): String =
    SimpleDateFormat(pattern, Locale.KOREA).format(this)

fun Int.toAnimalType(): AnimalType =
    when (this) {
        R.id.rb_mammal_write_post, R.id.rb_mammal -> AnimalType.MAMMEL
        R.id.rb_bird_write_post, R.id.rb_bird -> AnimalType.BIRD
        R.id.rb_reptiles_write_post, R.id.rb_reptiles -> AnimalType.REPTILES
        R.id.rb_amphibians_write_post, R.id.rb_amphibians -> AnimalType.AMPHIBIANS
        R.id.rb_fish_write_post, R.id.rb_fish -> AnimalType.FISH
        R.id.rb_arthropods_write_post, R.id.rb_arthropods -> AnimalType.ARTHROPODS
        else -> AnimalType.WRONG_TYPE
    }

fun convertUrlToFile(context: Context, url: String): Single<File> {
    return Single.create { emitter ->
        Glide.with(context)
            .asBitmap()
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(object : CustomTarget<Bitmap>(SIZE_ORIGINAL, SIZE_ORIGINAL) {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    val file = File.createTempFile("JPEG_", ".jpg", context.cacheDir)
                    FileOutputStream(file)
                        .use { resource.compress(Bitmap.CompressFormat.JPEG, 100, it) }
                    file.deleteOnExit()
                    emitter.onSuccess(file)
                }

                override fun onLoadCleared(placeholder: Drawable?) {}

                override fun onLoadFailed(errorDrawable: Drawable?) {}
            })
    }
}