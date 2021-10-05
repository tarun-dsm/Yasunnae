package com.semicolon.data.remote.response

import com.google.gson.annotations.SerializedName
import com.semicolon.domain.entity.PostApplicationEntity

data class PostApplicationResponse(

    @SerializedName("")
    val applications: List<PostApplication>
)

data class PostApplication(

    @SerializedName("application_id")
    val applicationId: Int,

    @SerializedName("applicant_id")
    val applicantId: Int,

    @SerializedName("application_date")
    val applicationDate: String,

    @SerializedName("is_accepted")
    val isAccepted: Boolean,

    @SerializedName("application_nickname")
    val applicantNickname: String,

    @SerializedName("administration_division")
    val administrationDivision: String
)

fun PostApplication.toEntity() = PostApplicationEntity(
    applicationId = this.applicationId,
    applicantId = this.applicantId,
    applicationDate = this.applicationDate,
    isAccepted = this.isAccepted,
    applicantNickname = this.applicantNickname,
    administrationDivision = this.administrationDivision
)