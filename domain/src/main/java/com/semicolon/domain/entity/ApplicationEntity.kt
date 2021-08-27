package com.semicolon.domain.entity

data class ApplicationEntity(

    val id: Int,

    val postName: String,

    val postId: Int,

    val isAccepted: Boolean,

    val isEnd: Boolean,

    val applicationDate: String
)
