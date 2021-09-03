package com.semicolon.domain.entity

data class ProfilePostEntity(

    val id: Int,

    val title: String,

    val createdAt: String,

    val nickname: String,

    val firstImagePath: String,

    val protector_id: String,

    val protector_nickname: String
)
