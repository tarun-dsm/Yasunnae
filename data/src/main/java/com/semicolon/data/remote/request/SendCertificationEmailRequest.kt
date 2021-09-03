package com.semicolon.data.remote.request

import com.google.gson.annotations.SerializedName

data class SendCertificationEmailRequest(

    @SerializedName("email")
    val email: String
)