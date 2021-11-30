package com.semicolon.domain.entity

data class ReviewEntity(

    val id: Int,

    val nickname: String,

    val grade: Double,

    val comment: String,

    val createdAt: String,

    val isMyReview: Boolean
)