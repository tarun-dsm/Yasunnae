package com.semicolon.data.remote.request

import com.google.gson.annotations.SerializedName

data class EmailRequest(

    @SerializedName("email")
    val email: String,

    @SerializedName("number")
    val number: String
)
