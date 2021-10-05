package com.semicolon.data.remote.response

import com.google.gson.annotations.SerializedName
import com.semicolon.domain.entity.ReviewEntity

data class ProfileReviewResponse(

    @SerializedName("reviews")
    val reviews: List<ProfileReview>
)

data class ProfileReview(

    @SerializedName("id")
    val id: Int,

    @SerializedName("nickname")
    val nickname: String,

    @SerializedName("grade")
    val grade: Double,

    @SerializedName("comment")
    val comment: String,

    @SerializedName("created_at")
    val createdAt: String
)

fun ProfileReview.toEntity() = ReviewEntity(
    id = this.id,
    nickname = this.nickname,
    grade = this.grade,
    comment = this.comment,
    createdAt = this.createdAt
)