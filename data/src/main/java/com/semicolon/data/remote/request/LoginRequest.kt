package com.semicolon.data.remote.request

import com.google.gson.annotations.SerializedName
import com.semicolon.domain.param.LoginParam

data class LoginRequest(

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String
)

fun LoginParam.toRequestParam() = LoginRequest(
    email = this.email,
    password = this.password
)