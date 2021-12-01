package com.semicolon.domain.entity

data class ApplicationEntity(

    val id: Int,

    val postName: String,

    val postId: Int,

    val firstImagePath: String,

    val protectionStartDate: String,

    val protectionEndDate: String,

    val administrationDivision: String,

    val isAccepted: Boolean,

    val isEnd: Boolean
)