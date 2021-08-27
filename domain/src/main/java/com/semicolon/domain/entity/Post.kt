package com.semicolon.domain.entity

data class Post(

    val id: Int,

    val title: String,

    val firstImagePath: String,

    val administrationDivision: String,

    val protectionStartDate: String,

    val protectionEndDate: String
)
