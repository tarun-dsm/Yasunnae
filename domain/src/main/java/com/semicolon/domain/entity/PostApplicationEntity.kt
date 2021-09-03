package com.semicolon.domain.entity

data class PostApplicationEntity(

    val applicationId: Int,

    val applicantId: Int,

    val applicationDate: String,

    val isAccepted: Boolean,

    val applicantNickname: String,

    val administrationDivision: String
)