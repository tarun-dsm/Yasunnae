package com.semicolon.data.remote.response

import com.google.gson.annotations.SerializedName
import com.semicolon.domain.entity.ProfileEntity

data class ProfileResponse(

    @SerializedName("nickname")
    val nickname: String,

    @SerializedName("avg_grade")
    val avgGrade: Double,

    @SerializedName("rating")
    val rating: String,

    @SerializedName("age")
    val age: Int,

    @SerializedName("sex")
    val sex: String,

    @SerializedName("experience")
    val experience: String,

    @SerializedName("location_confirm")
    val locationConfirm: Boolean,

    @SerializedName("experience_raising_pet")
    val experienceRaisingPet: Boolean,

    @SerializedName("administration_division")
    val administrationDivision: String
)

fun ProfileResponse.toEntity() = ProfileEntity(
    nickname = this.nickname,
    avgGrade = this.avgGrade,
    rating = this.rating,
    age = this.age,
    sex = this.sex,
    experience = this.experience,
    locationConfirm = this.locationConfirm,
    experienceRaisingPet = this.experienceRaisingPet,
    administrationDivision = this.administrationDivision
)
