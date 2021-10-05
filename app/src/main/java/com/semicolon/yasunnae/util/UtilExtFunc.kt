package com.semicolon.yasunnae.util

import java.text.SimpleDateFormat
import java.util.*

fun String.toPrettyDate(): String {
    val date = SimpleDateFormat(
        "yyyy-MM-dd`T`HH:mm:SS.SSSS", Locale.KOREA
    ).parse(this)
    return if (date != null) "${date.year}/${date.month}/${date.day} ${date.hours}:${date.minutes}" else ""
}