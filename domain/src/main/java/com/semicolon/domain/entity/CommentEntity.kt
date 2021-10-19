package com.semicolon.domain.entity

data class CommentEntity(

    val id: Int,

    val nickname: String,

    val comment: String,

    val createdAt: String,

    val isUpdated: Boolean,

    val updatedAt: String
)