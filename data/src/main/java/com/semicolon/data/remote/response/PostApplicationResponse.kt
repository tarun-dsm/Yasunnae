package com.semicolon.data.remote.response

import com.google.gson.annotations.SerializedName
import com.semicolon.domain.entity.PostApplicationEntity

data class PostApplicationResponse(

    @SerializedName("applications")
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

    @SerializedName("applicant_nickname")
    val applicantNickname: String,

    @SerializedName("administration_division")
    val administrationDivision: String,

    @SerializedName("is_written_review")
    val isWrittenReview: Boolean
)

fun PostApplication.toEntity() = PostApplicationEntity(
    applicationId = this.applicationId,
    applicantId = this.applicantId,
    applicationDate = this.applicationDate,
    isAccepted = this.isAccepted,
    applicantNickname = this.applicantNickname,
    administrationDivision = this.administrationDivision,
    isWrittenReview = isWrittenReview
)