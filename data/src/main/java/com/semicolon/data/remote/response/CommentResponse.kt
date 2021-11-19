package com.semicolon.data.remote.response

import com.google.gson.annotations.SerializedName
import com.semicolon.domain.entity.CommentEntity

data class CommentResponse(

    @SerializedName("comments")
    val comments: List<Comment>
)

data class Comment(

    @SerializedName("id")
    val id: Int,

    @SerializedName("nickname")
    val nickname: String,

    @SerializedName("comment")
    val comment: String,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("is_updated")
    val isUpdated: Boolean,

    @SerializedName("updated_at")
    val updatedAt: String?,

    @SerializedName("is_mine")
    val isMine: Boolean
)

fun Comment.toEntity() = CommentEntity(
    id = this.id,
    nickname = this.nickname,
    comment = this.comment,
    createdAt = this.createdAt,
    isUpdated = this.isUpdated,
    updatedAt = this.updatedAt ?: "",
    isMine = this.isMine
)