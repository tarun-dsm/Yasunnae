package com.semicolon.domain.entity

data class PostApplicationEntity(

    val applicationId: Int,

    val applicant_id: Int,

    val applicationDate: String,

    val isAccepted: Boolean,

    val applicantNickname: String,

    val administrationDivision: String
)