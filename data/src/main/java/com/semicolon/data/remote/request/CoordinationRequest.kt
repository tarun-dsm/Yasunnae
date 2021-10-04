package com.semicolon.data.remote.request

import com.google.gson.annotations.SerializedName
import com.semicolon.domain.param.CoordinateParam

data class CoordinateRequest(

    @SerializedName("longitude")
    val longitude: Double,

    @SerializedName("latitude")
    val latitude: Double
)

fun CoordinateParam.toRequestParam() = CoordinateRequest(
    longitude = this.longitude,
    latitude = this.latitude
)