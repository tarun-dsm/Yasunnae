package com.semicolon.domain.entity

data class PostEntity(

    val id: Int,

    val title: String,

    val firstImagePath: String,

    val administrationDivision: String,

    val protectionStartDate: String,

    val protectionEndDate: String
)
