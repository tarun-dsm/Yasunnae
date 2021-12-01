package com.semicolon.data.remote.request

import com.google.gson.annotations.SerializedName
import com.semicolon.domain.param.RegisterAccountParam

data class RegisterAccountRequest(

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("nickname")
    val nickname: String,

    @SerializedName("age")
    val age: Int,

    @SerializedName("sex")
    val sex: String,

    @SerializedName("is_experience_raising_pet")
    val isExperienceRaisingPet: Boolean,

    @SerializedName("experience")
    val experience: String?
)

fun RegisterAccountParam.toRequestParam() = RegisterAccountRequest(
        email = this.email,
        password = this.password,
        nickname = this.nickname,
        age = this.age,
        sex = this.sex.toString(),
        isExperienceRaisingPet = this.isExperienceRaisingPet,
        experience = this.experience
    )