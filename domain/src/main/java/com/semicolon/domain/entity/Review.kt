package com.semicolon.domain.entity

data class Review(

    val id: Int,

    val nickname: String,

    val grade: Double,

    val comment: String,

    val created_at: String
)